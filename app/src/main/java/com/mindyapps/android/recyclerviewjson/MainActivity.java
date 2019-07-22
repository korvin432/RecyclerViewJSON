package com.mindyapps.android.recyclerviewjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.onItemClickListener, View.OnClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerView;
    private EditText etDomain;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    ItemObject postItem;

    private String json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        etDomain = findViewById(R.id.et_domain);

        mExampleList = new ArrayList<>();


        etDomain.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    parseJSON();
                    return true;
                }
                return false;
            }
        });


        Button btnCompare = findViewById(R.id.compareBtn);
        btnCompare.setOnClickListener(this);
    }

    private void parseJSON(){
        String domain = etDomain.getText().toString();
        final int count = 20;
        String url = "https://api.vk.com/method/wall.get?domain="+ domain +"&v=5.92&count=" + count +
                "&access_token=eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()){
                    json = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            postItem = gson.fromJson(json, ItemObject.class);

                            for (int i = 0; i < count; i++) {
                                mExampleList.add(new ExampleItem(postItem.getImageUrl(i), postItem.getText(i), postItem.getLikes(i)));
                                mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                                mRecyclerView.setAdapter(mExampleAdapter);
                                mExampleAdapter.setOnItemClickListener(MainActivity.this);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getText());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.compareBtn:
                Intent intent = new Intent(this, CompareActivity.class);
                startActivity(intent);
                break;
        }
    }
}

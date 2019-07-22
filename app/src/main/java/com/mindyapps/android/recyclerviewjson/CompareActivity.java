package com.mindyapps.android.recyclerviewjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class CompareActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "qwwe";

    private EditText firstGroup;
    private EditText secondGroup;

    private TextView tvFirstName;
    private TextView tvSecondName;

    private TextView tvResult;

    private ImageView firstPicture;
    private ImageView secondPicture;

    private Button btnCompare;
    private String json = null;
    private String json2 = null;
    private String json3 = null;
    private String json4 = null;

    ArrayList<Integer> firstUsers = new ArrayList<>();
    MembersObject membersObject;
    List<Integer> members = new ArrayList<>();


    ArrayList<Integer> secondUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        firstGroup = findViewById(R.id.firstGroup);
        secondGroup = findViewById(R.id.secondGroup);

        tvFirstName = findViewById(R.id.firstName);
        tvSecondName = findViewById(R.id.secondName);

        firstPicture = findViewById(R.id.firstImage);
        secondPicture = findViewById(R.id.secondImage);

        tvResult = findViewById(R.id.resultText);

        btnCompare = findViewById(R.id.compare);
        btnCompare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compare:
                String firstId = firstGroup.getText().toString();
                String secondId = secondGroup.getText().toString();

                getUsersCount(firstId, secondId);

                break;
        }
    }

    private void setMembers(String Id, int countd, boolean isFirst) {
        String token = "eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";
        int page = 0;
        int limit = 1000;
        int offset;


        do {
            offset = page * limit;

            //Получаем список пользователей
            String url = "https://api.vk.com/method/groups.getMembers?" +
                    "group_id=" + Id + "&offset=" + offset + "&count=" + limit + "&v=5.101&access_token=" + token;

            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: error");
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        json = response.body().string();

                        Gson gson = new Gson();
                        membersObject = gson.fromJson(json, MembersObject.class);
                        members = membersObject.getIds();
                        Log.d("qwwe", "members: " + Arrays.toString(members.toArray()));
                        Log.d(TAG, "members size: " + members.size());
                    }
                }
            });

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isFirst) {
                firstUsers.addAll(members);
            } else {
                secondUsers.addAll(members);
            }
            //Увеличиваем страницу
            page++;

            Log.d(TAG, "page: " + page);
            Log.d(TAG, "count: " + countd + " offset: " + offset);

        } while (countd > offset + limit);
    }



    private void getUsersCount(final String firstId, final String secondId) {
        String url = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=" + firstId + "&v=5.101&access_token=eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    json = response.body().string();

                    CompareActivity.this.runOnUiThread(() -> {
                        Gson gson = new Gson();
                        membersObject = gson.fromJson(json, MembersObject.class);
                        int count = membersObject.getCount();

                        if (count != 0) {
                            setGroupInfo(firstId, secondId);

                            setMembers(firstId, count, true);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getSecondUsersCount(secondId);
                        }
                    });
                }
            }
        });

    }

    private void getSecondUsersCount(final String secondId) {
        String url = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=" + secondId + "&v=5.101&access_token=eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    json4 = response.body().string();

                    CompareActivity.this.runOnUiThread(() -> {
                        Gson gson = new Gson();
                        membersObject = gson.fromJson(json4, MembersObject.class);
                        int count = membersObject.getCount();

                        if (count != 0) {
                            setMembers(secondId, count, false);

                            Set<Integer> firstUsersSet = new HashSet<Integer>(firstUsers);
                            Set<Integer> set3 = new HashSet<>(firstUsersSet);
                            Set<Integer> secondUsersSet = new HashSet<>(secondUsers);

                            set3.retainAll(secondUsersSet);

                            Log.d(TAG, "same: " + Arrays.toString(set3.toArray()));
                            tvResult.setText("Одинаковых подписчиков: " + set3.size() + "\n" +
                                    "Их id: " + Arrays.toString(set3.toArray()));
                        }
                    });
                }
            }
        });

    }

    private void setGroupInfo(String firstId, String secondId){
        String url = "https://api.vk.com/method/groups.getById?group_id="+ firstId + "&v=5.101&access_token=eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";

        String secondUrl = "https://api.vk.com/method/groups.getById?group_id="+ secondId + "&v=5.101&access_token=eb10c7b2eb10c7b2eb10c7b2c9eb4f5d0ceeb10eb10c7b2b1015515a9f950f8572e4862";


        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    json2 = response.body().string();

                    CompareActivity.this.runOnUiThread(() -> {
                        Gson gson = new Gson();
                        GroupObject groupObject = gson.fromJson(json2, GroupObject.class);

                        String name = groupObject.getName();
                        String pictureUrl = groupObject.getPic();

                        Log.d(TAG, "1name: " + name);
                        tvFirstName.setText(name);
                        if (pictureUrl != null){
                            Picasso.with(getBaseContext()).load(pictureUrl).fit().centerInside().into(firstPicture);
                        } else {
                            firstPicture.setVisibility(View.INVISIBLE);
                        }

                    });
                }
            }
        });

        OkHttpClient secondClient = new OkHttpClient();
        okhttp3.Request secondRequest = new okhttp3.Request.Builder().url(secondUrl).build();
        secondClient.newCall(secondRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    json3 = response.body().string();

                    CompareActivity.this.runOnUiThread(() -> {
                        Gson gson = new Gson();
                        GroupObject groupObject = gson.fromJson(json3, GroupObject.class);

                        String name = groupObject.getName();
                        String pictureUrl = groupObject.getPic();


                        tvSecondName.setText(name);
                        if (pictureUrl != null){
                            Picasso.with(getBaseContext()).load(pictureUrl).fit().centerInside().into(secondPicture);
                        } else {
                            secondPicture.setVisibility(View.INVISIBLE);
                        }

                    });
                }
            }
        });
    }
}

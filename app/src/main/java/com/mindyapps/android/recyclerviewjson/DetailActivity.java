package com.mindyapps.android.recyclerviewjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.mindyapps.android.recyclerviewjson.MainActivity.EXTRA_CREATOR;
import static com.mindyapps.android.recyclerviewjson.MainActivity.EXTRA_LIKES;
import static com.mindyapps.android.recyclerviewjson.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        if (imageUrl != null){
            Picasso.with(this).load(imageUrl).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes: "+ likeCount);

    }
}

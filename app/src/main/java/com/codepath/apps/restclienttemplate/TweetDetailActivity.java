package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {
    private ImageView ivProfileImage;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvBody;
    private TextView tvTime;
    private TextView tvDate;
    private TextView tvRetweets;
    private TextView tvLikes;
    Tweet tweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ivProfileImage = findViewById(R.id.ivDetailProfileImage);
        tvName = findViewById(R.id.tvDetailName);
        tvScreenName = findViewById(R.id.tvDetailScreenName);
        tvBody = findViewById(R.id.tvDetailBody);
        tvTime = findViewById(R.id.tvDetailTime);
        tvDate = findViewById(R.id.tvDetailDate);
        tvRetweets = findViewById(R.id.tvDetailRetweetCount);
        tvLikes = findViewById(R.id.tvDetailLikeCount);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
        tvName.setText(tweet.user.name);
        tvScreenName.setText(tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.time);
        tvDate.setText(tweet.day);

        if(tweet.retweets.isEmpty()) {
            tvRetweets.setVisibility(View.GONE);
        }
        else
            tvRetweets.setText(tweet.retweets);
        if(tweet.likes.isEmpty())
            tvLikes.setVisibility(View.GONE);
        else
            tvLikes.setText(tweet.likes);

    }
}
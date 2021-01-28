package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String timeStamp;
    public long id;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.timeStamp = calculateTime(tweet.createdAt);
        tweet.id = jsonObject.getLong("id");
        return tweet;
    }

    private static String calculateTime(String createdAt)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date date = new Date();
        try {
            date  = dateFormat.parse(createdAt);
        } catch (ParseException e) {
            Log.e("Time", "parse error", e);
            e.printStackTrace();
        }

      //  Log.i("Time", date.toString());

        PrettyTime p  = new PrettyTime();
        String time= p.format(date);

        return time;

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}

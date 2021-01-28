package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String timeStamp;
    public String time;
    public String day;
    Date date;
    public long id;
    public int retweetCount;
    public int likeCount;
    public String retweet;
    public String like;
    public String retweets;
    public String likes;

    public Tweet(){ }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.date = getDate(tweet.createdAt);
        tweet.timeStamp = calculateTimeStamp(tweet.date);
        tweet.time = getTime(tweet.date);
        tweet.day = getDay(tweet.date);
        tweet.id = jsonObject.getLong("id");
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.likeCount = jsonObject.getInt("favorite_count");
        tweet.retweets = getRetweets(tweet.retweetCount);
        tweet.likes = getLikes(tweet.likeCount);
        tweet.retweet = getRetweet(tweet.retweetCount);
        tweet.like = getLike(tweet.likeCount);
        return tweet;
    }

    private static String getLike(int likeCount) {
        if(likeCount == 0)
            return "";
        return Integer.toString(likeCount);
    }

    private static String getRetweet(int retweetCount) {
        if(retweetCount == 0)
            return "";
        return Integer.toString(retweetCount);
    }

    private static String getLikes(int likeCount) {
        if(likeCount == 0)
            return "";
       // if(likeCount < 1000){
            return Integer.toString(likeCount) + " Likes";
      //  }
       /* double value = likeCount;
        int placement = 0;
        int divisor = 10;
        while(value > 999){
            value/=divisor;
            placement++;
        }
        if(placement == 0){
            return Integer.toString(likeCount) + " Likes";
        }
        if(placement == 1){
            return Double.toString(value) + "K Likes";
        }*/
    }

    private static String getRetweets(int retweetCount) {
        if(retweetCount == 0)
            return "";
        return Integer.toString(retweetCount) + " Retweets";

    }

    private static String getDay(Date date) {
        String format = "%d/%d/%d";
        return String.format(format, date.getMonth()+1, date.getDate(), date.getYear()+1900);
    }

    private static String getTime(Date date) {
        String format = "%02d:%02d";
        return String.format(format, date.getHours(), date.getMinutes());
    }

    private static Date getDate(String createdAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date date = new Date();
        try {
            date  = dateFormat.parse(createdAt);
        } catch (ParseException e) {
            Log.e("Time", "parse error", e);
            e.printStackTrace();
        }

        return date;
    }

    private static String calculateTimeStamp(Date date)  {
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

package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public String timeStamp;

    @ColumnInfo
    public String time;

    @ColumnInfo
    public String day;

    @Ignore
    Date date;

    @ColumnInfo
    public int retweetCount;

    @ColumnInfo
    public int likeCount;

    @ColumnInfo
    public String retweet;

    @ColumnInfo
    public String retweets;

    @ColumnInfo
    public String like;

    @ColumnInfo
    public String likes;

    @Ignore
    public List<String> mediaUrls;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;


    public Tweet(){ }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
       /* for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String s = it.next();
            Log.i("keys", s);
        }*/

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
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
        tweet.mediaUrls = getMedia(jsonObject.getJSONObject("entities"));
        return tweet;
    }

    private static List<String> getMedia(JSONObject entities) {
        List<String> mediaUrls = new ArrayList<>();
        if(entities.has("media")){
            try {
                JSONArray mediaJsonArray = entities.getJSONArray("media");
                for(int i = 0; i < mediaJsonArray.length(); i++){
                    JSONObject urls = mediaJsonArray.getJSONObject(i);
                    if(urls.has("media_url_https"))
                        mediaUrls.add(urls.getString("media_url_https"));
                }

             /*   if(entities.getJSONArray("media").getJSONObject(0).has("media_url_https")){
                   // return entities.getJSONArray("media").getJSONObject(0).getString("media_url_https");
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mediaUrls;
    }

    private static String getLike(int likeCount) {
        if(likeCount == 0)
            return "";
        if(likeCount < 10000){
            return Integer.toString(likeCount);
        }
        double value = likeCount;
        int placement = 0;
        while(value >= 1000) {
            value/=1000;
            placement++;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        if(placement == 1){
            if(value%1.0 == 0)
                return Integer.toString((int)value) + "K";
            return df.format(value) + "K";
        }
        if(value%1.0 == 0)
            return Integer.toString((int) value) + "M";
        return df.format(value) + "M";
    }

    private static String getRetweet(int retweetCount) {
        if(retweetCount == 0)
            return "";
        if(retweetCount < 10000){
            return Integer.toString(retweetCount);
        }
        double value = retweetCount;
        int placement = 0;
        while(value >= 1000) {
            value/=1000;
            placement++;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        if(placement == 1){
            if(value%1.0 == 0)
                return Integer.toString((int) value) + "K";
            return df.format(value) + "K";
        }
        if(value%1.0 == 0)
            return Integer.toString((int) value) + "M";
        return df.format(value) + "M";
    }

    private static String getLikes(int likeCount) {
        if(likeCount == 0)
            return "";
        if(likeCount < 10000){
            return Integer.toString(likeCount) + " Likes";
        }
        double value = likeCount;
        int placement = 0;
        while(value >= 1000) {
            value/=1000;
            placement++;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        if(placement == 1){
            if(value%1.0 == 0)
                return Integer.toString((int) value) + "K Likes";
            return df.format(value) + "K Likes";
        }
        if(value%1.0 == 0)
            return Integer.toString((int) value) + "M Likes";
        return df.format(value) + "M Likes";
    }

    private static String getRetweets(int retweetCount) {
        if(retweetCount == 0)
            return "";
        if(retweetCount < 10000){
            return Integer.toString(retweetCount) + " Retweets";
        }
        double value = retweetCount;
        int placement = 0;
        while(value >= 1000) {
            value/=1000;
            placement++;
        }
        DecimalFormat df = new DecimalFormat("0.0");

        if(placement == 1){
            if(value%1.0 == 0)
                return Integer.toString((int) value) + "K Retweets";
            return df.format(value) + "K Retweets";
        }
        if(value%1.0 == 0)
            return Integer.toString((int) value) + "M Retweets";
        return df.format(value) + "M Retweets";

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

package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    //Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id, Tweet.timeStamp AS tweet_timeStamp, Tweet.time AS tweet_time, Tweet.day AS tweet_day, Tweet.retweetCount AS tweet_retweetCount, Tweet.likeCount AS tweet_likeCount, Tweet.like AS tweet_like, Tweet.retweet AS tweet_retweet, Tweet.likes AS tweet_likes, Tweet.retweets AS tweet_retweets, Tweet.mediaUrls AS tweet_mediaUrls
    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id," +
            " Tweet.timeStamp AS tweet_timeStamp, Tweet.time AS tweet_time, Tweet.day AS tweet_day, " +
            "Tweet.retweetCount AS tweet_retweetCount, Tweet.likeCount AS tweet_likeCount, Tweet.like AS tweet_like," +
            "Tweet.retweet  AS tweet_retweet, Tweet.likes AS tweet_likes, Tweet.retweets AS tweet_retweets," +
            "User.* FROM Tweet INNER JOIN User ON Tweet.userId = User.id " +
            "ORDER BY Tweet.id DESC LIMIT 5")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}

package com.zthulj.blog.service;

import com.zthulj.blog.dto.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterService {

    @Value("${twitter.consumer.key}")
    private String consumerKey;

    @Value("${twitter.consumer.secret}")
    private String consumerSecret;

    @Value("${twitter.access.token}")
    private String accessToken;

    @Value("${twitter.token.secret}")
    private String tokenSecret;

    @Value("${host}")
    private String host;

    public void sendTweet(Article article) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(tokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            StatusUpdate update = new StatusUpdate(createTweet(article));
            twitter.updateStatus(update);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public String createTweet(Article article) {
        StringBuilder builder = new StringBuilder(article.getTitle() + "\n");
        builder.append(host + "/" + article.getCategory() + "/" + article.getLink());
        return builder.toString();
    }

}

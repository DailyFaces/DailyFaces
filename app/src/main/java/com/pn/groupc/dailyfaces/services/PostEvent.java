package com.pn.groupc.dailyfaces.services;

import com.pn.groupc.dailyfaces.interfaces.*;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONObject;

public class PostEvent {

    private PusherOptions options;
    private Pusher pusher;
    private Channel channel;
    private PostInterfaces postInterfaces;



    public void initPusher() {
        postInterfaces = new PostInterfaces();
        options = new PusherOptions();
        options.setCluster("eu");
        // initialize Pusher
        pusher = new Pusher("0635cce56c05162df332", options);

        // connect to the Pusher API
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("pusher state :  " + change.getCurrentState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("pusher error: " + message);
                System.out.println("pusher Exception: " + e);
            }
        }, ConnectionState.ALL);


        // subscribe to our "messages" channel
        channel = pusher.subscribe("my-channel");
//fi
        channel.bind("post-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                try {
                    JSONObject jsonData = new JSONObject(data);
                    String type  = jsonData.get("type").toString();
                    switch (type){
                        case "new_post":
                            newPost(jsonData);
                            break;
                        case "new_my_day":
                            newMyDay(jsonData);
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        });

    }

    public void newPost(JSONObject post) {
        postInterfaces.news_feed.addFirst(post);
        System.out.println(postInterfaces.news_feed);

    }

    public void newMyDay(JSONObject my_day) {
        postInterfaces.my_day.addFirst(my_day);
        System.out.println(postInterfaces.my_day);

    }
}

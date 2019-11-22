package com.pn.groupC.dailyfaces.services;
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


    public void initPusher() {
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

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                try {
                    JSONObject jsonData = new JSONObject(data);
                    System.out.println(jsonData.get("type"));
                    newPost(jsonData);
                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        });

    }

    public void newPost(JSONObject data) {
        System.out.println(data);
    }

    public void newMessage(JSONObject data) {

    }

    public void newMyDay() {

    }
}

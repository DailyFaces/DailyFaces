package com.pn.groupC.dailyfaces.services;

import com.pn.groupC.dailyfaces.interfaces.InboxInterface;
import com.pn.groupC.dailyfaces.interfaces.PostInterfaces;
import com.pn.groupC.dailyfaces.interfaces.chatInterface;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONObject;

import java.util.HashMap;

public class ChatEvent {
    private PusherOptions options;
    private Pusher pusher;
    private Channel channel;
    private chatInterface ChatInterface;
    private InboxInterface inboxInterface;

    public void initPusher() {
        ChatInterface = new chatInterface();
        options = new PusherOptions();
        options.setCluster("eu");
        // initialize Pusher
        pusher = new Pusher("5e71cf67fedab567924b", options);

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
                    newChat(jsonData);
                    toInbox(jsonData);
                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        });

    }
    public void newChat(JSONObject msg) {
        ChatInterface.chats.add(msg);
        System.out.println(ChatInterface.chats);

    }
    public void toInbox(JSONObject msg){
        //not yet final
        try {
            HashMap msgs =  new HashMap<String, String>();
            msgs.put(msg.get("sender"),msg.get("message"));
            inboxInterface.inbox.add(msgs);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

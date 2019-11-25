package com.pn.groupC.dailyfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pn.groupC.dailyfaces.services.pusher.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PostEvent postEvnt = new PostEvent();
        postEvnt.initPusher();
    }
}

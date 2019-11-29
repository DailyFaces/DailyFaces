package com.pn.groupc.dailyfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pn.groupc.dailyfaces.services.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatEvent chatevt = new ChatEvent();
        chatevt.initPusher();
    }
}

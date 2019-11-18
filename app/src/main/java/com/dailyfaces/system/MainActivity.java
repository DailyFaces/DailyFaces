package com.yol.dailyfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.yol.adroidpermission.push_notifications.PostEvent;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PostEvent evnt= new PostEvent();
        evnt.initPusher();
    }

}

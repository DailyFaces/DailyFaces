package com.example.myapplication;

public interface Callback {

    void onError(String message);

    void onResponse(Object response);
}

package com.example.myapplication;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyResponseListener {



    void onResponse(JSONObject response);
    void onError(VolleyError message);
}

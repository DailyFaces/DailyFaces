package com.example.services.Network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface NetworkCallback {
    public void notifySuccess(JSONObject response);
    public void notifyError(VolleyError error);


}

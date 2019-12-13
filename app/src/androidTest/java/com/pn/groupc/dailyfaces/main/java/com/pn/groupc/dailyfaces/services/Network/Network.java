package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Network {
    private ImageLoader imageLoader;
    NetworkCallback mResultCallback = null;
    Context mContext;

    public Network(NetworkCallback resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    final String url = "http://172.16.11.44:3000";

    public void request(String api, final Map<String, String> parameter) {

        Map<String, String> params = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    this.url + api, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Success Callback
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            if (mResultCallback != null)
                                mResultCallback.notifyError(error);
                        }
                    });
            jsonObjReq.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 10000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 10000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                    if (error instanceof TimeoutError) {
                        // your stuf
                        if (mResultCallback != null)
                            mResultCallback.notifyError(error);
                    }

                }
            });
            Singleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }


    }

//    public void get(String api){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, this.url+api, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if (mResultCallback != null)
//                            mResultCallback.notifySuccess(response);
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (mResultCallback != null)
//                            mResultCallback.notifyError(error);
//                    }
//                });
//
//// Access the RequestQueue through your singleton class.
//        Singleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
//    }
}
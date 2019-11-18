package com.example.myapplication;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Network  {

    // static variable single_instance of type Singleton
    private static Network single_instance = null;

    // variable of type String
    public String url;
    public String route;
    public String responseString;
    public VolleyError volleyError;
    public Context context;

    // private constructor restricted to this class itself
    private Network(String url,String route, Context context)
    {
        this.url = url;
        this.route = route;
        this.context = context;
    }

    public String getResponseString(){
        return responseString;
    }

    public VolleyError getVolleyError(){
        return volleyError;
    }

    public void setResponseString(String response){
        this.responseString = response;
    }
    // static method to create instance of Singleton class
    public static Network getInstance(String url,String route, Context context)
    {
        if (single_instance == null)
            single_instance = new Network(url, route,context);

        return single_instance;
    }

    public String volley(){

        this.responseString = "response is null";
        this.volleyError = null;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url+this.route,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //CODE HERE FOR THE RESPONSE
                        

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //CODE HERE FOR HANDLING ERROR
                volleyError = error;
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return responseString;
    }

}

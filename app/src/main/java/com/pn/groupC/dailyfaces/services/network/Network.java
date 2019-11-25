package com.example.myapplication;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Network{
    private ImageLoader imageLoader;
    IResult mResultCallback = null;
    Context mContext;

    Network(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    final String url = "http://172.16.11.44:9000";
    public void request( String api, final Map<String,String> parameter) {

        Map<String, String>  params = new HashMap<String, String>();
        for (Map.Entry<String,String> entry : parameter.entrySet()) {
            params.put(entry.getKey(),entry.getValue());
        }
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    this.url+api, new JSONObject(params),
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

            Singleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }

    private void loadImage(String url, ImageView imageView){
            //link to search (https://coderzpassion.com/android-working-volley-library/)

        imageLoader = Singleton.getInstance(mContext)
                .getImageLoader();
//        imageLoader.get(url, ImageLoader.getImageListener(imageView,
//                R.drawable., android.R.drawable
//                        .ic_dialog_alert));
//        imageView.setImage.setImageUrl(url, imageLoader);
    }
}
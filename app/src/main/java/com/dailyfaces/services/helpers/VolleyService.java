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

public class VolleyService{
    private ImageLoader imageLoader;
    IResult mResultCallback = null;
    Context mContext;

    VolleyService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void getDataVolley(final String requestType, String url, final Map<String,String> parameter) {

        Map<String, String>  params = new HashMap<String, String>();
        for (Map.Entry<String,String> entry : parameter.entrySet()) {
            params.put(entry.getKey(),entry.getValue());
        }
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Success Callback
                            if (mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            if (mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                        }
                    });

            MySingleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }

    private void loadImage(String url, ImageView imageView){
            //link to search (https://coderzpassion.com/android-working-volley-library/)

        imageLoader = MySingleton.getInstance(mContext)
                .getImageLoader();
//        imageLoader.get(url, ImageLoader.getImageListener(imageView,
//                R.drawable., android.R.drawable
//                        .ic_dialog_alert));
//        imageView.setImage.setImageUrl(url, imageLoader);
    }
}
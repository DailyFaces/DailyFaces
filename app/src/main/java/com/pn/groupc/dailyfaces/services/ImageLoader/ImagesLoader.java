package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

//IMPORTANT NOTE: IMPORT NOSTRA13 DEPENDENCY ON GRADLE
//implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
public class ImagesLoader {
    Context mContext;
    ImageInterface imageCallback = null;
    NetworkCallback mResultCallback = null;

    public ImagesLoader(ImageInterface imageCallback, Context context) {
        this.imageCallback = imageCallback;
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getImage(String api) {
        final String url = "http://172.16.11.44:3000";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url + api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (mResultCallback != null)
                            mResultCallback.notifySuccess(response);
                        try {
                            String res = response.getString("url");
                            loadImage(url + res);     //WHAT TO DO AFTER HERE ???
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mResultCallback != null)
                            mResultCallback.notifyError(error);
                    }
                });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
                    if (mResultCallback != null)
                        mResultCallback.notifyError(error);
                }

            }
        });
// Access the RequestQueue through your singleton class.
        Singleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

    public void loadImage(String uri) {
        loaderInterface(uri);
    }

    public void loaderInterface(String uri) {

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(mContext));

        // Load image, decode it to Bitmap and return Bitmap to callback
        imgLoader.loadImage(uri, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (imageCallback != null) {
                    imageCallback.notifyError(failReason);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                if (imageCallback != null) {
                    imageCallback.notifySuccess(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}

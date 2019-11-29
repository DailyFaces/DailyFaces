package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//IMPORTANT NOTE: IMPORT NOSTRA13 DEPENDENCY ON GRADLE
//implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
public class ImageLoaderTest {
    Context mContext;
    ImageInterface imageCallback = null;
    //convert base64 to bitmap
    IResult mCallback = null;

    public ImageLoaderTest(ImageInterface imageCallback) {
        this.imageCallback = imageCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void loadImage(String url) throws IOException {
//
//        // Load image, decode it to Bitmap and return Bitmap to callback
//        imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                // Do whatever you want with Bitmap
//            }
//        });
//        imageLoader = Singleton.getInstance(mContext)
//                .getImageLoader();
//    }

    public void getImage(String api) {
        final String uri = "http://172.16.10.44:3000";
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    uri + api, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String uri = response.getString("uri");        //THIS LOCATION NEEDS TESTING!
                                // Load image, decode it to Bitmap and return Bitmap to callback
                                loaderInterface(uri);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Failure Callback
                            if (mCallback != null) {
                                mCallback.notifyError(error);
                            }
                        }
                    });

            Singleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {
        }
    }

    public void loadImage(String uri) {
        loaderInterface(uri);
    }

    public void loaderInterface(String uri){

        ImageLoader imgLoader = ImageLoader.getInstance();

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

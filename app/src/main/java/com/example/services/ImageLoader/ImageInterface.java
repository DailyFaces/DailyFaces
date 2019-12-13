package com.example.services.ImageLoader;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.assist.FailReason;

public interface ImageInterface {
    public void notifySuccess(Bitmap image);
    public void notifyError(FailReason error);
}

package com.mgilangjanuar.dev.civitasearch.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Fatah on 1/23/2018.
 */

public class BitmapStringTransform {
    public static Bitmap toBitmap(String data) {
        byte[] imageAsBytes = Base64.decode(data.getBytes(), Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static String toString(Bitmap image) {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayInputStream);
        byte[] bytes = byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}

package com.mgilangjanuar.dev.civitasearch.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;

/**
 * Created by Fatah on 1/23/2018.
 */

public class PermissionPerformer {
    private static final int MY_PERMISSIONS_REQUEST = 123;
    private static int CURRENT_API = Build.VERSION.SDK_INT;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissionExternalStorage(final Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((BaseActivity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("External storage permission is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((BaseActivity) context, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
            else {
                ActivityCompat.requestPermissions((BaseActivity) context, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
            }
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean checkPermissionCamera(final Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((BaseActivity) context, Manifest.permission.CAMERA)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("Camera permission is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
            else {
                ActivityCompat.requestPermissions((BaseActivity) context, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
            }
            return false;
        }
        else {
            return true;
        }
    }
}

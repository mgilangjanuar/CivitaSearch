package com.mgilangjanuar.dev.civitasearch.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Fatah on 1/15/2018.
 */

public class LocalStorage {
    public static void insert(Context context, String id, String title, String content) {
        SharedPreferences preferences = context.getSharedPreferences(id, Context.MODE_PRIVATE);

        if ("Phone".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.PHONE, new HashSet<String>()));
            set.add(content);

            preferences.edit()
                    .putStringSet(Constant.PHONE, new HashSet<>(set))
                    .apply();
        }
        else if ("Email".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.EMAIL, new HashSet<String>()));
            set.add(content);

            preferences.edit()
                    .putStringSet(Constant.EMAIL, new HashSet<>(set))
                    .apply();
        }
        else if ("Address".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.ADDRESS, new HashSet<String>()));
            set.add(content);

            preferences.edit()
                    .putStringSet(Constant.ADDRESS, new HashSet<>(set))
                    .apply();
        }
        else if ("Photo".equals(title)) {
            preferences.edit()
                    .remove(Constant.PHOTO)
                    .putString(Constant.PHOTO, content)
                    .apply();
        }
    }

    public static List<String> select(Context context, String id, String type) {
        SharedPreferences preferences = context.getSharedPreferences(id, Context.MODE_PRIVATE);

        if ("Phone".equals(type)) {
            return new ArrayList<>(preferences.getStringSet(Constant.PHONE, new HashSet<String>()));
        }
        else if ("Email".equals(type)) {
            return new ArrayList<>(preferences.getStringSet(Constant.EMAIL, new HashSet<String>()));
        }
        else if ("Address".equals(type)) {
            return new ArrayList<>(preferences.getStringSet(Constant.ADDRESS, new HashSet<String>()));
        }
        else if ("Photo".equals(type)) {
            List<String> list = new ArrayList<>();
            list.add(preferences.getString(Constant.PHOTO, null));

            return list;
        }

        return new ArrayList<>();
    }

    public static void remove(Context context, String id, String title, String content) {
        SharedPreferences preferences = context.getSharedPreferences(id, Context.MODE_PRIVATE);

        if ("Phone".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.PHONE, new HashSet<String>()));
            set.remove(content);

            preferences.edit()
                    .putStringSet(Constant.PHONE, new HashSet<>(set))
                    .apply();
        }
        else if ("Email".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.EMAIL, new HashSet<String>()));
            set.remove(content);

            preferences.edit()
                    .putStringSet(Constant.EMAIL, new HashSet<>(set))
                    .apply();
        }
        else if ("Address".equals(title)) {
            Set<String> set = new HashSet<>(preferences.getStringSet(Constant.ADDRESS, new HashSet<String>()));
            set.remove(content);

            preferences.edit()
                    .putStringSet(Constant.ADDRESS, new HashSet<>(set))
                    .apply();
        }
        else if ("Photo".equals(title)) {
            preferences.edit()
                    .remove(Constant.PHOTO)
                    .apply();
        }
    }
}

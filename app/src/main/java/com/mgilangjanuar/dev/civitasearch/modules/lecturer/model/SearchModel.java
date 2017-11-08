package com.mgilangjanuar.dev.civitasearch.modules.lecturer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class SearchModel {

    @SerializedName("uid")
    private String id;

    private String name;

    @SerializedName("school_name")
    private String schoolName;

    private String status;

    private List<String> titles;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTitles() {
        return titles;
    }
}

package com.mgilangjanuar.dev.civitasearch.modules.school.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class SchoolModel {

    @SerializedName("uuid")
    private String id;

    private String name;

    public SchoolModel() {
    }

    public SchoolModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

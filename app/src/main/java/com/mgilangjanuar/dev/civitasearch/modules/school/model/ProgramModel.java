package com.mgilangjanuar.dev.civitasearch.modules.school.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class ProgramModel {

    @SerializedName("uuid")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

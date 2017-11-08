package com.mgilangjanuar.dev.civitasearch.modules.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class SearchModel {

    @SerializedName("uid")
    private String id;

    private String name;

    @SerializedName("student_id")
    private String studentId;

    private String degree;

    @SerializedName("school_name")
    private String schoolName;

    @SerializedName("program_name")
    private String programName;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDegree() {
        return degree;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getProgramName() {
        return programName;
    }
}

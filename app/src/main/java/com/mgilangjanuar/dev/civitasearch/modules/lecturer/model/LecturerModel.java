package com.mgilangjanuar.dev.civitasearch.modules.lecturer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class LecturerModel {

    @SerializedName("uid")
    private String id;

    private String name;

    @SerializedName("program_name")
    private String programName;

    private String sex;

    private String position;

    @SerializedName("highest_education")
    private String highestEducation;

    private String employment;

    private String status;

    @SerializedName("education_history")
    private List<EducationHistory> educationHistories;

    @SerializedName("lecture_history")
    private List<LectureHistory> lectureHistories;

    private List<Research> researches;

    private Photo photo;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgramName() {
        return programName;
    }

    public String getSex() {
        return sex;
    }

    public String getPosition() {
        return position;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public String getEmployment() {
        return employment;
    }

    public String getStatus() {
        return status;
    }

    public List<EducationHistory> getEducationHistories() {
        return educationHistories;
    }

    public List<LectureHistory> getLectureHistories() {
        return lectureHistories;
    }

    public List<Research> getResearches() {
        return researches;
    }

    public Photo getPhoto() {
        return photo;
    }

    public class EducationHistory {

        @SerializedName("school_name")
        private String schoolName;

        @SerializedName("title_received")
        private String titleReceived;

        @SerializedName("year_graduated")
        private int yearGraduated;

        private String degree;

        public String getSchoolName() {
            return schoolName;
        }

        public String getTitleReceived() {
            return titleReceived;
        }

        public int getYearGraduated() {
            return yearGraduated;
        }

        public String getDegree() {
            return degree;
        }
    }

    public class LectureHistory {

        private String term;

        @SerializedName("course_code")
        private String courseCode;

        @SerializedName("course_name")
        private String courseName;

        @SerializedName("class_code")
        private String classCode;

        @SerializedName("school_name")
        private String schoolName;

        @Override
        public String toString() {
            return courseName + " (" + courseCode + ")\n" + term;
        }
    }

    private class Research {

        private String title;

        private String field;

        private String institution;

        private int year;
    }

    private class Photo {

        private String type;

        private String data;
    }
}

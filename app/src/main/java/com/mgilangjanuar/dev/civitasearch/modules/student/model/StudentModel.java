package com.mgilangjanuar.dev.civitasearch.modules.student.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class StudentModel {

    @SerializedName("uid")
    private String id;

    private String name;

    private String sex;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("school_name")
    private String schoolName;

    @SerializedName("program_name")
    private String programName;

    private String batch;

    private String status;

    @SerializedName("status_history")
    private List<StatusHistory> statusHistories;

    @SerializedName("course_history")
    private List<CourseHistory> courseHistories;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getProgramName() {
        return programName;
    }

    public String getBatch() {
        return batch;
    }

    public String getStatus() {
        return status;
    }

    public List<StatusHistory> getStatusHistories() {
        return statusHistories;
    }

    public List<CourseHistory> getCourseHistories() {
        return courseHistories;
    }

    public final class StatusHistory {

        private String term;

        private String status;

        @SerializedName("credits_taken")
        private int creditsTaken;

        public String getTerm() {
            return term;
        }

        public String getStatus() {
            return status;
        }

        public int getCreditsTaken() {
            return creditsTaken;
        }
    }

    public final class CourseHistory {

        private String term;

        private List<Course> courses;

        public String getTerm() {
            return term;
        }

        public List<Course> getCourses() {
            return courses;
        }

        public String toHtmlString() {
            StringBuilder coursesString = new StringBuilder();
            for (Course course : courses) {
                coursesString.append("<p>&#8226; " + course.name + " (" + course.credits + " credits" + ")</p>");
            }
            return coursesString.toString();
        }

        private final class Course {

            private String code;

            private String name;

            private int credits;
        }
    }
}

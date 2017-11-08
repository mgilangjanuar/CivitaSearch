package com.mgilangjanuar.dev.civitasearch.service;

import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.ProgramModel;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.SchoolModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.SearchModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.StudentModel;
import com.mgilangjanuar.dev.civitasearch.util.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class Api {

    private static String baseUrl = Constant.BASE_URL;
    private static Retrofit adapter = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Service create() {
        Service result = adapter.create(Service.class);
        baseUrl = Constant.BASE_URL;
        adapter = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return result;
    }

    public static void setBaseUrl(String baseUrl) {
        Api.baseUrl = baseUrl;
        adapter = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface Service {

        @GET("schools/search")
        Call<List<SchoolModel>> searchSchools(@Query("keyword") String keyword);

        @GET("schools/{id}/programs")
        Call<List<ProgramModel>> getPrograms(@Path("id") String schoolId);

        @GET("students/search")
        Call<List<SearchModel>> searchStudents(@Query("school_uuid") String schoolId, @Query("program_uuid") String programId, @Query("keyword") String keyword);

        @GET("students/search")
        Call<List<SearchModel>> searchStudents(@Query("school_uuid") String schoolId, @Query("keyword") String keyword);

        @GET("students/{id}")
        Call<StudentModel> getStudent(@Path("id") String studentId);

        @GET("lecturers/search")
        Call<List<com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.SearchModel>> searchLecturers(@Query("school_uuid") String schoolId, @Query("keyword") String keyword);

        @GET("lecturers/{id}")
        Call<LecturerModel> getLecturer(@Path("id") String lecturerId);
    }
}

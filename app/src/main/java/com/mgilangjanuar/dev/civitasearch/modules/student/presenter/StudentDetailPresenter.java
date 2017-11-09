package com.mgilangjanuar.dev.civitasearch.modules.student.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.StudentDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.StudentModel;
import com.mgilangjanuar.dev.civitasearch.service.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class StudentDetailPresenter {

    private StudentDetailListener listener;

    public StudentDetailPresenter(StudentDetailListener listener) {
        this.listener = listener;
    }

    public void getStudent(String id) {
        Api.create().getStudent(id).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                StudentModel model = response.body();

                List<ViewContentModel> list = new ArrayList<>();
                list.add(new ViewContentModel("Name", model.getName()));
                list.add(new ViewContentModel("Student ID", model.getStudentId()));
                list.add(new ViewContentModel("Batch", model.getBatch()));
                list.add(new ViewContentModel("Program", model.getProgramName()));
                list.add(new ViewContentModel("School", model.getSchoolName()));
                list.add(new ViewContentModel("Status", model.getStatus()));
                list.add(new ViewContentModel("Sex", model.getSex()));
                list.add(new ViewContentModel("Status Histories", "View all status histories", model.getStatusHistories()));
                list.add(new ViewContentModel("Course Histories", "View all course histories", model.getCourseHistories()));
                listener.onSuccess(new StudentDetailAdapter(list));
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}

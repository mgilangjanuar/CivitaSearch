package com.mgilangjanuar.dev.civitasearch.modules.student.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.school.model.ProgramModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentSearchAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.ProgramListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.StudentSearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.SearchModel;
import com.mgilangjanuar.dev.civitasearch.service.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class StudentSearchPresenter {

    private String schoolId;
    private ProgramListener programListener;
    private StudentSearchListener studentSearchListener;

    public StudentSearchPresenter(String schoolId, ProgramListener programListener, StudentSearchListener studentSearchListener) {
        this.schoolId = schoolId;
        this.programListener = programListener;
        this.studentSearchListener = studentSearchListener;
    }

    public void searchPrograms() {
        Api.create().getPrograms(schoolId).enqueue(new Callback<List<ProgramModel>>() {
            @Override
            public void onResponse(Call<List<ProgramModel>> call, Response<List<ProgramModel>> response) {
                programListener.onRetrievePrograms(response.body());
            }

            @Override
            public void onFailure(Call<List<ProgramModel>> call, Throwable t) {
                programListener.onError(t.getMessage());
            }
        });
    }

    public void searchStudentsWithProgram(String programName, String keyword, List<ProgramModel> programModelList) {
        String programId = null;
        for (ProgramModel program : programModelList) {
            if (programName.equals(program.getName())) {
                programId = program.getId();
                break;
            }
        }
        Api.create().searchStudents(schoolId, programId, keyword).enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                StudentSearchAdapter adapter = new StudentSearchAdapter(response.body());
                studentSearchListener.onRetrieveStudents(adapter);
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                studentSearchListener.onError(t.getMessage());
            }
        });
    }

    public void searchStudents(String keyword) {
        Api.create().searchStudents(schoolId, keyword).enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                StudentSearchAdapter adapter = new StudentSearchAdapter(response.body());
                studentSearchListener.onRetrieveStudents(adapter);
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                studentSearchListener.onError(t.getMessage());
            }
        });
    }
}

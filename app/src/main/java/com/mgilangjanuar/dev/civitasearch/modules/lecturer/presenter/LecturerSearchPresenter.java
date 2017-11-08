package com.mgilangjanuar.dev.civitasearch.modules.lecturer.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerSearchAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener.LecturerSearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.SearchModel;
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

public class LecturerSearchPresenter {

    private LecturerSearchListener listener;

    public LecturerSearchPresenter(LecturerSearchListener listener) {
        this.listener = listener;
    }

    public void searchLecturers(String schoolId, String keyword) {
        Api.create().searchLecturers(schoolId, keyword).enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                listener.onRetrieveLecturer(new LecturerSearchAdapter(response.body()));
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}

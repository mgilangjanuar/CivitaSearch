package com.mgilangjanuar.dev.civitasearch.modules.school.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.school.adapter.SchoolAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.school.listener.SearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.SchoolModel;
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

public class SchoolPresenter {

    private SearchListener listener;

    public SchoolPresenter(SearchListener listener) {
        this.listener = listener;
    }

    public void search(String keyword) {
        Api.create().searchSchools(keyword).enqueue(new Callback<List<SchoolModel>>() {
            @Override
            public void onResponse(Call<List<SchoolModel>> call, Response<List<SchoolModel>> response) {
                SchoolAdapter adapter = new SchoolAdapter(response.body());
                listener.onSuccess(adapter);
            }

            @Override
            public void onFailure(Call<List<SchoolModel>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}

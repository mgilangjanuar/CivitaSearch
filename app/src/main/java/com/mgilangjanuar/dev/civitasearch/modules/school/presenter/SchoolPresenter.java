package com.mgilangjanuar.dev.civitasearch.modules.school.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.school.adapter.SchoolAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.school.listener.SearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.SchoolModel;
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

    public SchoolAdapter buildInitAdapter() {
        List<SchoolModel> list = new ArrayList<>();
        list.add(new SchoolModel("0D1E63E9-CBFB-4546-A242-875C310083A5", "Universitas Indonesia"));
        list.add(new SchoolModel("A1E8C356-48EF-4871-AF3E-85079443F952", "Institut Teknologi Bandung"));
        list.add(new SchoolModel("8ED1D0CE-F122-4B37-A849-25F81B335395", "Universitas Gadjah Mada"));
        list.add(new SchoolModel("5AADF3DF-D0F8-4A17-BDFD-6108FC5CE405", "Universitas Airlangga"));
        list.add(new SchoolModel("828FB966-3733-430E-86FF-909B764E2523", "Institut Pertanian Bogor"));
        list.add(new SchoolModel("B5FDF347-931D-46C8-BB95-20B7B1C8B5BA", "Universitas Padjadjaran"));
        list.add(new SchoolModel("54D01BF3-8255-474B-B96D-4426135EED01", "Universitas Diponegoro"));
        list.add(new SchoolModel("6D124142-E798-4F62-874B-CE435B5409F1", "Universitas Muhammadiyah Surakarta"));
        list.add(new SchoolModel("89C82DA3-7B28-4305-82C4-347DAE042847", "Institut Teknologi Sepuluh Nopember"));
        list.add(new SchoolModel("99A3B7AF-6470-4D18-8B06-09941E663B07", "Universitas Brawijaya"));
        return new SchoolAdapter(list);
    }
}

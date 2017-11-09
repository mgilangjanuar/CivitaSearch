package com.mgilangjanuar.dev.civitasearch.modules.lecturer.presenter;

import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener.LecturerDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;
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

public class LecturerDetailPresenter {

    private LecturerDetailListener listener;

    public LecturerDetailPresenter(LecturerDetailListener listener) {
        this.listener = listener;
    }

    public void getLecturer(String lecturerId) {
        Api.create().getLecturer(lecturerId).enqueue(new Callback<LecturerModel>() {
            @Override
            public void onResponse(Call<LecturerModel> call, Response<LecturerModel> response) {
                LecturerModel model = response.body();

                List<ViewContentModel> list = new ArrayList<>();
                list.add(new ViewContentModel("Name", model.getName()));
                list.add(new ViewContentModel("Employment", model.getEmployment()));
                list.add(new ViewContentModel("Position", model.getPosition()));
                list.add(new ViewContentModel("Status", model.getStatus()));
                list.add(new ViewContentModel("Program", model.getProgramName()));
                list.add(new ViewContentModel("Highest Education", model.getHighestEducation()));
                list.add(new ViewContentModel("Sex", model.getSex()));

                int countEducationHistories = 0;
                for (LecturerModel.EducationHistory educationHistory : model.getEducationHistories()) {
                    if (educationHistory.getYearGraduated() > 0) {
                        countEducationHistories++;
                        break;
                    }
                }
                if (countEducationHistories > 0) {
                    list.add(new ViewContentModel("Education Histories", "View all education Histories", model.getEducationHistories()));
                }

                if (!model.getLectureHistories().isEmpty()) {
                    list.add(new ViewContentModel("Lecture Histories", "View all lecture histories", model.getLectureHistories()));
                }

                listener.onSuccess(new LecturerDetailAdapter(list));
            }

            @Override
            public void onFailure(Call<LecturerModel> call, Throwable t) {

            }
        });
    }
}

package com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener;

import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface LecturerDetailListener {

    void onSuccess(LecturerDetailAdapter adapter, LecturerModel.Photo photo);

    void onError(String error);
}

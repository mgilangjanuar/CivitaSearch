package com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener;

import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerDetailAdapter;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface LecturerDetailListener {

    void onSuccess(LecturerDetailAdapter adapter);

    void onError(String error);
}

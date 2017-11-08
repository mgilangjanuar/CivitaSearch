package com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener;

import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerSearchAdapter;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface LecturerSearchListener {

    void onRetrieveLecturer(LecturerSearchAdapter adapter);

    void onError(String error);
}

package com.mgilangjanuar.dev.civitasearch.modules.student.listener;

import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentDetailAdapter;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface StudentDetailListener {

    void onSuccess(StudentDetailAdapter adapter);

    void onError(String error);
}

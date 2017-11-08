package com.mgilangjanuar.dev.civitasearch.modules.student.listener;

import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentSearchAdapter;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface StudentSearchListener {

    void onRetrieveStudents(StudentSearchAdapter adapter);

    void onError(String error);

}

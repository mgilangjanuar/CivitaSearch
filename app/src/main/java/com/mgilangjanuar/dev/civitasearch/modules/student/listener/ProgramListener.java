package com.mgilangjanuar.dev.civitasearch.modules.student.listener;

import com.mgilangjanuar.dev.civitasearch.modules.school.model.ProgramModel;

import java.util.List;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface ProgramListener {

    void onRetrievePrograms(List<ProgramModel> list);

    void onError(String error);
}

package com.mgilangjanuar.dev.civitasearch.modules.school.listener;

import com.mgilangjanuar.dev.civitasearch.modules.school.adapter.SchoolAdapter;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public interface SearchListener {

    void onSuccess(SchoolAdapter adapter);

    void onError(String message);
}

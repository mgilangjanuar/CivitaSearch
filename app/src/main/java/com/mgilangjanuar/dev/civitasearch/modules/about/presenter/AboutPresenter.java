package com.mgilangjanuar.dev.civitasearch.modules.about.presenter;

import android.content.Context;
import android.content.pm.PackageManager;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.about.adapter.AboutAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class AboutPresenter {

    private Context context;

    public AboutPresenter(Context context) {
        this.context = context;
    }

    public AboutAdapter buildAdapter() {
        List<ViewContentModel> list = new ArrayList<>();
        list.add(new ViewContentModel("Application Name", context.getResources().getString(R.string.app_name)));
        try {
            list.add(new ViewContentModel("Version", context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        list.add(new ViewContentModel("Data Resource", "Click for view data resource"));
        list.add(new ViewContentModel("Repository", "Click for view repository"));
        list.add(new ViewContentModel("Rate and Feedback", "Click for give rate and feedback"));
        return new AboutAdapter(list);
    }
}

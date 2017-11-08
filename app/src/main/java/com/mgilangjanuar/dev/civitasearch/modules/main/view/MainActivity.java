package com.mgilangjanuar.dev.civitasearch.modules.main.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.about.view.AboutFragment;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.view.LecturerSearchFragment;
import com.mgilangjanuar.dev.civitasearch.modules.student.view.StudentSearchFragment;
import com.mgilangjanuar.dev.civitasearch.util.BottomNavigationViewUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public String schoolId;
    public String schoolName;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private MenuItem currentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schoolId = getIntent().getStringExtra("id");
        schoolName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(schoolName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BottomNavigationViewUtil.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectMenu(item);
                return true;
            }
        });

        MenuItem menuItem;
        if (savedInstanceState != null) {
            int selectedItem = savedInstanceState.getInt("arg_selected_item", 0);
            menuItem = bottomNavigationView.getMenu().findItem(selectedItem);
        } else {
            menuItem = bottomNavigationView.getMenu().getItem(0);
        }

        try {
            selectMenu(menuItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int findLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void selectMenu(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_student:
                fragment = StudentSearchFragment.newInstance();
                break;
            case R.id.action_lecturer:
                fragment = LecturerSearchFragment.newInstance();
                break;
            case R.id.action_about:
                fragment = AboutFragment.newInstance();
                break;
        }

        if (currentItem == null || !item.equals(currentItem)) {
            item.setChecked(true);
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.container, fragment);
            beginTransaction.commit();
        }
        currentItem = item;
    }
}

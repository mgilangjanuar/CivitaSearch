package com.mgilangjanuar.dev.civitasearch.modules.main.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.about.view.AboutFragment;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.view.LecturerSearchFragment;
import com.mgilangjanuar.dev.civitasearch.modules.student.view.StudentSearchFragment;
import com.mgilangjanuar.dev.civitasearch.util.BottomNavigationViewUtil;
import com.mgilangjanuar.dev.civitasearch.util.TabPagerAdapterUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public String schoolId;
    public String schoolName;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.container)
    ViewPager viewPager;

    private MenuItem currentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schoolId = getIntent().getStringExtra("id");
        schoolName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(schoolName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabPagerAdapterUtil adapterUtil = new TabPagerAdapterUtil(getSupportFragmentManager());
        adapterUtil.addFragment(StudentSearchFragment.newInstance(), schoolName);
        adapterUtil.addFragment(LecturerSearchFragment.newInstance(), schoolName);
        adapterUtil.addFragment(AboutFragment.newInstance(), getString(R.string.more));
        viewPager.setAdapter(adapterUtil);

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
        selectMenu(menuItem);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectMenu(bottomNavigationView.getMenu().getItem(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
        switch (item.getItemId()) {
            case R.id.action_student:
                viewPager.setCurrentItem(0);
                getSupportActionBar().setTitle(schoolName);
                break;
            case R.id.action_lecturer:
                viewPager.setCurrentItem(1);
                getSupportActionBar().setTitle(schoolName);
                break;
            case R.id.action_about:
                viewPager.setCurrentItem(2);
                getSupportActionBar().setTitle(getString(R.string.more));
                break;
        }

        if (currentItem == null || !item.equals(currentItem)) {
            item.setChecked(true);
        }
        currentItem = item;
    }
}

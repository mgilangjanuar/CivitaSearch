package com.mgilangjanuar.dev.civitasearch.modules.student.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.StudentDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.presenter.StudentDetailPresenter;

import butterknife.BindView;

public class StudentDetailActivity extends BaseActivity implements StudentDetailListener {

    @BindView(R.id.details)
    RecyclerView details;

    private ProgressDialog progressDialog;
    private StudentDetailPresenter presenter = new StudentDetailPresenter(this);

    @Override
    public int findLayout() {
        return R.layout.activity_student_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.student_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = buildLoadingDialog();
        progressDialog.show();

        setupRecyclerView(details);

        presenter.getStudent(getIntent().getStringExtra("uid"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(StudentDetailAdapter adapter) {
        progressDialog.cancel();
        details.setAdapter(adapter);
    }

    @Override
    public void onError(String error) {
        progressDialog.cancel();
        showSnackbar(error);
    }
}

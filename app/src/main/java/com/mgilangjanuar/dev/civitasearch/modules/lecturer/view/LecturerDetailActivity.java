package com.mgilangjanuar.dev.civitasearch.modules.lecturer.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener.LecturerDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.presenter.LecturerDetailPresenter;

import butterknife.BindView;

public class LecturerDetailActivity extends BaseActivity implements LecturerDetailListener {

    @BindView(R.id.details)
    RecyclerView details;

    private ProgressDialog progressDialog;
    private LecturerDetailPresenter presenter = new LecturerDetailPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.lecturer_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupRecyclerView(details);

        progressDialog = buildLoadingDialog();
        progressDialog.show();

        presenter.getLecturer(getIntent().getStringExtra("uid"));
    }

    @Override
    public int findLayout() {
        return R.layout.activity_lecturer_detail;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(LecturerDetailAdapter adapter) {
        progressDialog.cancel();
        if (adapter.getItemCount() == 0) {
            showSnackbar(getString(R.string.empty_result));
        } else {
            details.setAdapter(adapter);
        }
    }

    @Override
    public void onError(String error) {
        progressDialog.cancel();
        showSnackbar(error);
    }
}

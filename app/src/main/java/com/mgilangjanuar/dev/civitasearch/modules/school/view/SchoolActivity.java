package com.mgilangjanuar.dev.civitasearch.modules.school.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.school.adapter.SchoolAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.school.listener.SearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.school.presenter.SchoolPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolActivity extends BaseActivity implements SearchListener {

    @BindView(R.id.school_search)
    EditText schoolSearch;

    @BindView(R.id.schools)
    RecyclerView schools;

    @BindView(R.id.submit)
    Button submit;

    private ProgressDialog progressDialog;

    private SchoolPresenter presenter = new SchoolPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView(schools);
        schoolSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submit.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int findLayout() {
        return R.layout.activity_school;
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        hideKeyboard();
        if (TextUtils.isEmpty(schoolSearch.getText())) {
            showSnackbar(R.string.cannot_empty);
        } else {
            progressDialog = buildLoadingDialog();
            progressDialog.show();

            presenter.search(schoolSearch.getText().toString());
        }
    }

    @Override
    public void onSuccess(SchoolAdapter adapter) {
        progressDialog.cancel();
        schools.setAdapter(adapter);
        if (adapter.getItemCount() == 0) {
            showSnackbar(R.string.empty_result);
        }
    }

    @Override
    public void onError(String message) {
        progressDialog.cancel();
        showSnackbar(message);
    }
}

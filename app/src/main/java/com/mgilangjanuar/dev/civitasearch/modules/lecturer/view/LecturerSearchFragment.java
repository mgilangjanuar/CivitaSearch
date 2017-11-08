package com.mgilangjanuar.dev.civitasearch.modules.lecturer.view;

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
import com.mgilangjanuar.dev.civitasearch.base.BaseFragment;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerSearchAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener.LecturerSearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.presenter.LecturerSearchPresenter;
import com.mgilangjanuar.dev.civitasearch.modules.main.view.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LecturerSearchFragment extends BaseFragment implements LecturerSearchListener {

    @BindView(R.id.lecturer_search)
    EditText lecturerSearch;

    @BindView(R.id.lecturers)
    RecyclerView lecturers;

    @BindView(R.id.submit)
    Button submit;

    private ProgressDialog progressDialog;
    private LecturerSearchPresenter presenter = new LecturerSearchPresenter(this);

    public static LecturerSearchFragment newInstance() {
        LecturerSearchFragment fragment = new LecturerSearchFragment();
        return fragment;
    }

    @Override
    public void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        String schoolName = ((MainActivity) getActivity()).schoolName;
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(schoolName);

        ((BaseActivity) getActivity()).setupRecyclerView(lecturers);

        lecturerSearch.setOnKeyListener(new View.OnKeyListener() {
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
        return R.layout.fragment_lecturer_search;
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        ((BaseActivity) getActivity()).hideKeyboard();
        progressDialog = ((BaseActivity) getActivity()).buildLoadingDialog();
        progressDialog.show();

        if (TextUtils.isEmpty(lecturerSearch.getText())) {
            ((BaseActivity) getActivity()).showSnackbar(getString(R.string.cannot_empty));
        } else {
            presenter.searchLecturers(((MainActivity) getActivity()).schoolId, lecturerSearch.getText().toString());
        }
    }

    @Override
    public void onRetrieveLecturer(LecturerSearchAdapter adapter) {
        progressDialog.cancel();
        lecturers.setAdapter(adapter);
    }

    @Override
    public void onError(String error) {
        progressDialog.cancel();
        ((BaseActivity) getActivity()).showSnackbar(error);
    }
}

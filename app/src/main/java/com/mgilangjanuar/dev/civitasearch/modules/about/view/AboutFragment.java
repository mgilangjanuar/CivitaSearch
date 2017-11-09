package com.mgilangjanuar.dev.civitasearch.modules.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.base.BaseFragment;
import com.mgilangjanuar.dev.civitasearch.modules.about.presenter.AboutPresenter;

import butterknife.BindView;

public class AboutFragment extends BaseFragment {

    @BindView(R.id.about)
    RecyclerView about;

    private AboutPresenter presenter;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        presenter = new AboutPresenter(getContext());
        ((BaseActivity) getActivity()).setupRecyclerView(about);
        about.setAdapter(presenter.buildAdapter());
    }

    @Override
    public int findLayout() {
        return R.layout.fragment_about;
    }
}

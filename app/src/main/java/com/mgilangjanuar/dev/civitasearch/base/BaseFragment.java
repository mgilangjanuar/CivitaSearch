package com.mgilangjanuar.dev.civitasearch.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(findLayout(), container, false);
        ButterKnife.bind(this, view);
        initialize(savedInstanceState);
        return view;
    }

    public void initialize(@Nullable Bundle savedInstanceState) {
    }

    @LayoutRes
    public abstract int findLayout();
}

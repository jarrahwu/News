package com.stkj.android.locker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/22.
 */
public abstract class BaseFragment extends Fragment{

    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = onCreateViewImpl(inflater, container, savedInstanceState);
        mView.setBackgroundColor(getResources().getColor(R.color.app_background));
        mView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return mView;
    }

    public abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}

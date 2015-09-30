package com.stkj.android.locker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/30.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateImpl(savedInstanceState);
        mToolbar = ToolbarBuilder.build(this, R.layout.main_toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    protected abstract void onCreateImpl(Bundle savedInstanceState);
}

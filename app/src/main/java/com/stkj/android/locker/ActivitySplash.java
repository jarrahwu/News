package com.stkj.android.locker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.locker.R;

public class ActivitySplash extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityInit.start(this);
        finish();
    }

}

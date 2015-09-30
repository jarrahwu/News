package com.stkj.android.locker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.locker.R;

/**
 * Created by jarrah on 2015/9/15.
 */
public class ActivityLock extends FullScreenActivity {

    private static ActivityLock sActivity;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sActivity = this;
        setContentView(R.layout.activity_lock);
        LockerManager.showLocker(this);
    }

    public static void start(Context context) {
        Intent i = new Intent();
        i.setClass(context, ActivityLock.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void destroy() {
        if (sActivity != null) {
            sActivity.finish();
            sActivity.overridePendingTransition(0, android.R.anim.slide_out_right);
            sActivity = null;
        }
    }
}

package com.stkj.android.locker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by jarrah on 2015/9/18.
 * Window service 后台显示window
 */
public class LockerManager extends Service{

    public static final String ACTION_SHOW_WINDOW = "WindowService.ACTION.SHOW.WINDOW";
    public static final String ACTION_DISMISS_WINDOW = "WindowService.ACTION.DISMISS.WINDOW";

    private LockerWindow mLockerWindow;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null) {
            Log.from(this, "intent is null");
            return super.onStartCommand(intent, flags, startId);
        }

        if(ACTION_SHOW_WINDOW.equals(intent.getAction())) {
            Log.from(this, "ACTION_SHOW_WINDOW");
            onShowImpl();
            return super.onStartCommand(intent, flags, startId);
        }

        if(ACTION_DISMISS_WINDOW.equals(intent.getAction())) {
            Log.from(this, "ACTION_DISMISS_WINDOW");
            onDismissImpl();
            return super.onStartCommand(intent, flags, startId);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private LockerWindow getLockerWindow() {
        if (mLockerWindow == null) {
            mLockerWindow = new LockerWindow(this);
        }

        return mLockerWindow;
    }

    private void onDismissImpl() {
        getLockerWindow().dismiss();
    }

    private void onShowImpl() {
        getLockerWindow().show();
    }

    private static void start(Context context, String action) {
        Intent intent = new Intent(action);
        intent.setClass(context, LockerManager.class);
        context.startService(intent);
    }

    public static void showLocker(Context context) {
        start(context, ACTION_SHOW_WINDOW);
    }

    public static void dismissLocker(Context context) {
        start(context, ACTION_DISMISS_WINDOW);
    }

}

package com.stkj.android.locker;

import com.stkj.android.recyclergallery.ImageHelper;

/**
 * Created by jarrah on 2015/9/21.
 */
public class Application extends android.app.Application {

    private static Application sInstance;

    public static synchronized Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ImageHelper.initImageLoader(getApplicationContext());
    }
}

package com.stkj.android.locker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jarrah on 2015/9/23.
 */
public class TogglePreference {

    public static TogglePreference sInstance;
    public static Object mLock = new Object();

    public static TogglePreference getsInstance() {
        synchronized (mLock) {
            if(sInstance == null) {
                sInstance = new TogglePreference();
            }
        }
        return sInstance;
    }

    public static final String PREFERENCE_NAME = "toggle_preference";

    private static SharedPreferences getPreferences() {
        SharedPreferences preferences = Application.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences;
    }

    public static void toggle(long id) {
        boolean value = isChecked(id);
        getPreferences().edit().putBoolean(String.valueOf(id), !value).apply();
    }

    public static boolean isChecked(long id) {
        return getPreferences().getBoolean(String.valueOf(id), false);
    }

}

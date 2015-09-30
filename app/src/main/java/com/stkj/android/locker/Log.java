package com.stkj.android.locker;

/**
 * Created by jarrah on 2015/9/18.
 */
public class Log {
    public static final boolean DBG = true;

    public static void from(Object obj, String msg) {
        android.util.Log.e("Locker",  obj.toString() + "|" + msg);
    }
}

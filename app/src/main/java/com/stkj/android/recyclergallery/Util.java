package com.stkj.android.recyclergallery;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by jarrah on 2015/9/18.
 */
public class Util {

    public static void startChooseLockGeneric(Activity activity, int requestCode) {
        Intent i = new Intent();
        ComponentName cmp = new ComponentName("com.android.settings", "com.android.settings.ChooseLockGeneric");
        i.setComponent(cmp);
        activity.startActivityForResult(i, requestCode);
    }
}

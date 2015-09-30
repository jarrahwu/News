package com.stkj.android.locker;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 用来动态注册广播的service
 */
public class LockerService extends android.app.Service{

	private static LockerReceiver sLockerReceiver = new LockerReceiver();
	
	public static final String ACTION_DISABLE_LOCK = "ACTION.disable.lock";
	public static final String ACTION_ENABLE_LOCK = "ACTION.enable.lock";


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(intent != null) {
			if(intent.getAction() == ACTION_DISABLE_LOCK) {
				LockerReceiver.unregister(this, sLockerReceiver);
				stopSelf();
			}

			if(intent.getAction() == ACTION_ENABLE_LOCK) {
				LockerReceiver.register(this, sLockerReceiver);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public static void enableLocker(Context context) {
		Intent i = new Intent();
		i.setAction(LockerService.ACTION_ENABLE_LOCK);
		i.setClass(context, LockerService.class);
		context.startService(i);
	}

	public static void disableLocker(Context context) {
		Intent i = new Intent();
		i.setAction(LockerService.ACTION_DISABLE_LOCK);
		i.setClass(context, LockerService.class);
		context.startService(i);
	}
}

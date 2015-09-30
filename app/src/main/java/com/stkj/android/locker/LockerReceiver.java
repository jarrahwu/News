package com.stkj.android.locker;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.concurrent.atomic.AtomicBoolean;

public class LockerReceiver extends BroadcastReceiver {

	private static AtomicBoolean isRegistered = new AtomicBoolean(false);

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent == null)
			return;

		if (Intent.ACTION_SCREEN_OFF == intent.getAction()) {
			disableSysLock(context);
			enableLock(context);
		}
	}

	private void enableLock(Context context) {
		ActivityLock.start(context);
	}

	private void disableSysLock(Context context) {
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
		keyguardLock.disableKeyguard();// 解锁系统锁屏
	}

	public static void register(Context context, LockerReceiver lockerReceiver) {
		if (!isRegistered.get()) {
			IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
			context.registerReceiver(lockerReceiver, filter);
			isRegistered.set(true);
		}
	}

	public static void unregister(Context context, LockerReceiver lockerReceiver) {

		if (isRegistered.get()) {
			context.unregisterReceiver(lockerReceiver);
			isRegistered.set(false);
		}

	}

}

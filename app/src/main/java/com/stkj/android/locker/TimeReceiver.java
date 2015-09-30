package com.stkj.android.locker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.concurrent.atomic.AtomicBoolean;

public class TimeReceiver extends BroadcastReceiver{
	
	private TimeTicker mTimeTicker;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent == null) 
			return;
		
		if(Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
			if(mTimeTicker != null) {
				mTimeTicker.onTick();
			}
		}
	}
	
	public void setTimeTicker(TimeTicker ticker) {
		mTimeTicker = ticker;
	}
	
	public static abstract class TimeTicker {
		abstract void onTick();
	}
	
	
	private static AtomicBoolean isRegistered = new AtomicBoolean(false);
	private static TimeReceiver sTimeReceiver = new TimeReceiver();
	
	public static void register(Context context, TimeTicker ticker) {
		if(!isRegistered.get()) {
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_TIME_TICK);
			sTimeReceiver.setTimeTicker(ticker);
			context.registerReceiver(sTimeReceiver, filter);
			isRegistered.set(true);
		}
	}
	
	public static void unRegister(Context context) {
		if(isRegistered.get()) {
			context.unregisterReceiver(sTimeReceiver);
			isRegistered.set(false);
		}
	}

}

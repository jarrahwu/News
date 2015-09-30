package com.stkj.android.locker;

import android.content.Context;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

class SwipeGestureListener extends SimpleOnGestureListener {
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private Context mContext;

	public SwipeGestureListener(Context context) {
		mContext = context;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				onLeftSwipe();
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				onRightSwipe();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public void onRightSwipe() {
		
	}

	public void onLeftSwipe() {
		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}
}
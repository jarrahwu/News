package com.stkj.android.locker;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

public class WindowParamHelper {

	public static final int WINDOW_FLAG = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
			| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

	public static final int WINDOW_FLAG_FULL_SCREEN = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
			| WINDOW_FLAG;

	public static final int WINDOW_TYPE = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

	public static WindowManager.LayoutParams fullScreen(Context context) {
		int width = -1;
		int height = -1;
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(width,
				height, WINDOW_TYPE, WINDOW_FLAG_FULL_SCREEN,// 不获取输入焦点
				PixelFormat.TRANSLUCENT);

		lp.gravity = Gravity.TOP;// TOP can move only

		return lp;
	}
}

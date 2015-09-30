package com.stkj.android.locker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locker.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class LockerWindow {

    private GestureDetector mUnlockDetector;
    private View.OnTouchListener mTouchDelegate;

    private final Context mContext;
    private final WindowManager mWindowManager;
    private final LayoutInflater mLayoutInflater;

    private View mContentView;
    private FrameLayout mWindowContainer;

    private AtomicBoolean isLockWindowShow = new AtomicBoolean(false);
    private Shimmer shimmer;
    private ImageView mLockerImage;

    public LockerWindow(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setupSwipeGesture(context);
    }

    private void inflateLockView() {
        mWindowContainer = new FrameLayout(mContext);
        mContentView = mLayoutInflater.inflate(R.layout.lock_window, mWindowContainer, false);
        mLockerImage = (ImageView) mContentView.findViewById(R.id.lockerImage);
        mWindowContainer.addView(mContentView);
    }

    private void releaseLockView() {
        mWindowContainer = null;
        mContentView = null;
    }

    private void setupSwipeGesture(Context context) {
        mUnlockDetector = new GestureDetector(context,
                new SwipeGestureListener(mContext) {
                    @Override
                    public void onRightSwipe() {
                        dismiss();
                    }
                });

        mTouchDelegate = new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public boolean onTouch(View v, MotionEvent event) {
                return mUnlockDetector.onTouchEvent(event);
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void show() {
        if(!isLockWindowShow.get()) {
            inflateLockView();
            mWindowManager.addView(mWindowContainer, WindowParamHelper.fullScreen(mContext));
            Util.displayStorageWall(mLockerImage);

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            mContentView.setOnTouchListener(mTouchDelegate);

            ShimmerTextView myShimmerTextView = (ShimmerTextView) mContentView.findViewById(R.id.shimmer_tv);
            shimmer = new Shimmer();
            shimmer.start(myShimmerTextView);

            setTime();
            TimeReceiver.register(mContext, new TimeReceiver.TimeTicker() {

                @Override
                void onTick() {
                    setTime();
                }
            });

            isLockWindowShow.set(true);
        }

    }


    private void setTime() {
        Time t = new Time(Time.getCurrentTimezone());
        t.setToNow();
        TextView hour = (TextView) mContentView.findViewById(R.id.hour);
        hour.setText(String.format("%s:%s", "" + t.hour, "" + t.minute));

        TextView day = (TextView) mContentView.findViewById(R.id.day);
        day.setText(String.format("%s月%s日 星期%s", "" + (t.month + 1), ""
                + (t.monthDay), "" + (t.weekDay)));
    }

    public void dismiss() {
        if(isLockWindowShow.get()) {
            ViewCompat.animate(mContentView).scaleX(1.3f).scaleY(1.3f).alpha(0.1f)
                    .setInterpolator(new AccelerateInterpolator())
                    .setDuration(100)
                    .setListener(new ViewPropertyAnimatorListener() {

                        @Override
                        public void onAnimationStart(View arg0) {

                        }

                        @Override
                        public void onAnimationEnd(View arg0) {
                            TimeReceiver.unRegister(mContext);
                            ActivityLock.destroy();
                            mWindowManager.removeViewImmediate(mWindowContainer);
                            releaseLockView();
                        }

                        @Override
                        public void onAnimationCancel(View arg0) {

                        }
                    });

            isLockWindowShow.set(false);
        }
    }
}

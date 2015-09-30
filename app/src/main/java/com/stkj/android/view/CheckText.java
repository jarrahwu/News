package com.stkj.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import com.example.locker.R;
import com.stkj.android.locker.Log;

/**
 * Created by jarrah on 2015/9/29.
 */
public class CheckText extends TextView implements Checkable, View.OnClickListener {

    private boolean isChecked = false;
    private Drawable checkedDrawable;
    private Drawable normalDrawable;

    public CheckText(Context context) {
        super(context);
        init(context, null);
    }


    public CheckText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckText);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.CheckText_checked_drawable:
                    this.checkedDrawable = typedArray.getDrawable(R.styleable.CheckText_checked_drawable);
                    Log.from(this, "checked drawable " + this.checkedDrawable);
                    break;
                case R.styleable.CheckText_normal_drawable:
                    this.normalDrawable = typedArray.getDrawable(R.styleable.CheckText_normal_drawable);
                    Log.from(this, "normal drawable " + this.normalDrawable);
                    break;
                case R.styleable.CheckText_checked:
                    this.isChecked = typedArray.getBoolean(R.styleable.CheckText_checked, false);
                    Log.from(this, "is checked " + this.isChecked);
                    break;
            }
        }
        typedArray.recycle();
        setChecked(isChecked);
        setOnClickListener(this);
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
        if (this.isChecked && this.checkedDrawable != null) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.checkedDrawable, null);
        }

        if (!this.isChecked && this.normalDrawable != null) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.normalDrawable, null);
        }
    }


    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }

    @Override
    public void onClick(View v) {
        setChecked(!this.isChecked);
    }
}

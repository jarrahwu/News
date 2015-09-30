package com.stkj.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.locker.R;
import com.stkj.android.locker.Log;

/**
 * Created by jarrah on 2015/9/22.
 */
public class TextSwitch extends FrameLayout {

    private LayoutInflater mLayoutInflater;
    public android.widget.TextView title;
    public android.widget.TextView description;
    public android.support.v7.widget.SwitchCompat switchCompat;

    public TextSwitch(Context context) {
        super(context);
        init(context, null);
    }

    public TextSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mLayoutInflater = LayoutInflater.from(context);
        View v = mLayoutInflater.inflate(R.layout.text_switch, this, true);
        this.switchCompat = (SwitchCompat) v.findViewById(R.id.switchCompat);
        this.description = (TextView) v.findViewById(R.id.description);
        this.title = (TextView) v.findViewById(R.id.title);

        setAttributeValues(context, attrs);
    }

    private void setAttributeValues(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextSwitch);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.TextSwitch_switch_description:
                    CharSequence desc = typedArray.getText(R.styleable.TextSwitch_switch_description);
                    Log.from(this, "set desc" + desc);
                    if (!TextUtils.isEmpty(desc)) {
                        this.description.setText(desc);
                    }
                    break;
                case R.styleable.TextSwitch_switch_title:
                    CharSequence title = typedArray.getString(R.styleable.TextSwitch_switch_title);
                    if (!TextUtils.isEmpty(title)) {
                        this.title.setText(title);
                    }
                    break;
                case R.styleable.TextSwitch_switch_checked:
                    boolean checked = typedArray.getBoolean(R.styleable.TextSwitch_switch_checked, false);
                    this.switchCompat.setChecked(checked);
                    break;
            }
        }
        typedArray.recycle();

        if(TextUtils.isEmpty(this.description.getText())) {
            this.description.setVisibility(View.GONE);
        }
    }


}

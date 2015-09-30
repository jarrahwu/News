package com.stkj.android.recyclergallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by jarrah on 2015/8/17.
 */
public class MyLayoutManager extends GridLayoutManager {

    public MyLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return generateMyLayoutParams();
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return generateMyLayoutParams();
    }

    public RecyclerView.LayoutParams generateMyLayoutParams() {
        int width = getWidth() / getSpanCount();
        int height = (int) (width * 1.8f);
        LayoutParams lp = new LayoutParams(width, height);
        return lp;
    }
}

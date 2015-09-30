package com.stkj.android.recyclergallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyDecoration extends RecyclerView.ItemDecoration {


    public MyDecoration() {
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(3, 3, 3, 3);
    }


}
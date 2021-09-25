package com.greencross.medigene.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by insystemscompany on 2017. 6. 26..
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int divHeight;

    public DividerItemDecoration(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeight;
    }

}
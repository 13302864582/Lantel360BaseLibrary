package com.xiao360.baselibrary.dragMenu;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/***
 *
 * item间距
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int row = 1;
    private int space = 1;

    public SpaceItemDecoration(int row, int space) {
        this.row = row;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //左间距
        outRect.left = space;
        //右间距
        outRect.bottom = space;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

}

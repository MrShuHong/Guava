package com.core.ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/9/2
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int orientation;
    private float mDividerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
            Resources.getSystem().getDisplayMetrics());
    private int mDividerColor = Color.parseColor("#eeeeee");


    public GridDividerItemDecoration() {
    }

    public GridDividerItemDecoration(float dividerWidth, @ColorRes int color) {
        this.mDividerColor = color;
        this.mDividerWidth = dividerWidth;
    }


    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = dividerWidth;
    }

    public void setDividerColor(@ColorRes int color) {
        this.mDividerColor = color;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        drawVerticalDivider(canvas, parent);
        drawHorizontalDivider(canvas, parent);
    }

    /**
     * 画水平线
     * @param canvas
     * @param parent
     */
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mDividerColor);

        for (int index = 0; index < parent.getChildCount(); index++) {
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + (int)mDividerWidth;
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin ;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    /**
     * 画垂直线
     * @param canvas
     * @param parent
     */
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mDividerColor);

        for (int index = 0; index < parent.getChildCount(); index++) {
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + (int)mDividerWidth;
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, (int) mDividerWidth, (int) mDividerWidth);
    }
}

package com.example.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private int dividerWidth;
    private int leftPadding;
    private int topPadding;
    private int bottomPadding;
    private int rightPadding;
    private int dividerColor;
    private Paint mPaint;

    public CustomItemDecoration(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#eeeeee"));

    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                drawVerticalDivider(canvas, parent);
            } else if (orientation == LinearLayoutManager.HORIZONTAL) {

                drawHorizontalDivider(canvas, parent);
            }
        } else {
            throw new IllegalArgumentException("please use LinearLayoutManager");
        }
    }

    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + dividerWidth;
            canvas.drawRect(child.getLeft() + leftPadding, top, child.getRight() - rightPadding, bottom, mPaint);
        }
    }

    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + dividerWidth;
            canvas.drawRect(left, child.getTop() + topPadding, right, child.getBottom() - bottomPadding, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(leftPadding, 0, rightPadding, dividerWidth);
            } else if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.set(0, topPadding, dividerWidth, bottomPadding);
            }
        } else {
            throw new IllegalArgumentException("please use LinearLayoutManager");
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
    }
}

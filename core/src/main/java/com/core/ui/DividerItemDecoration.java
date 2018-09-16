package com.core.ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/9/2
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int orientation;
    private float mDividerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,
            Resources.getSystem().getDisplayMetrics());
    private int mDividerColor = Color.parseColor("#eeeeee");

    /*private int[] attrs = new int[]{
           android.R.attr.listDivider
    };
    private Drawable mDividerDrawable;*/

    public DividerItemDecoration(int orientation) {
        /*TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDividerDrawable = typedArray.getDrawable(0);
        typedArray.recycle();*/
        setOrientation(orientation);
    }

    public DividerItemDecoration(int orientation,float dividerWidth,@ColorRes int color ) {
        this.mDividerColor = color;
        this.mDividerWidth = dividerWidth;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation){
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("请设置正确的方向");
        }
        this.orientation = orientation;
    }

    public void setDividerWidth(float dividerWidth){
        this.mDividerWidth = dividerWidth;
    }

    public void setDividerColor(@ColorRes int color){
        this.mDividerColor = color;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL){
            drawVerticalDivider(canvas,parent);
        }else{
            drawHorizontalDivider(canvas,parent);
        }
    }

    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mDividerColor);
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        for (int index = 0; index < parent.getChildCount();index++){
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + layoutParams.rightMargin + Math.round(child.getTranslationX());
            int right = (int) (left + mDividerWidth);
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }

    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mDividerColor);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int index = 0; index < parent.getChildCount();index++){
            View child = parent.getChildAt(index);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + layoutParams.bottomMargin + Math.round(child.getTranslationY());
            int bottom = (int) (top + mDividerWidth);
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0, (int) mDividerWidth);
        }else{
            outRect.set(0,0, (int) mDividerWidth,0);
        }
    }
}

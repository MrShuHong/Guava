package com.demo.hencoder.day09;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/25
 */

public class TagLayout extends ViewGroup {

    private Rect[] childrensBounds;

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childrensBounds == null) {
            childrensBounds = new Rect[childCount];
        } else if (childrensBounds.length < childCount) {
            childrensBounds = Arrays.copyOf(childrensBounds, childCount);
        }
        int lineWidthUsed = 0;
        int widthUsed = 0;
        int heightUsed = 0;
        int maxHeight = 0;
        int maxWidth = 0;

        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            Rect childBounds = childrensBounds[index];
            measureChildWithMargins(childView, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed);

            //测量换行情况
            if (lineWidthUsed + childView.getMeasuredWidth() + getPaddingStart() + getPaddingEnd() >=
                    MeasureSpec.getSize(widthMeasureSpec)) {
                lineWidthUsed = 0;
                heightUsed += maxHeight;
                maxHeight = 0;
                measureChildWithMargins(childView, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed);
            }

            if (childBounds == null) {
                childBounds = childrensBounds[index] = new Rect();
            }
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + childView.getMeasuredWidth(), heightUsed + childView.getMeasuredHeight());

            lineWidthUsed += childView.getMeasuredWidth();
            maxHeight = Math.max(childView.getMeasuredHeight(), maxHeight);
            widthUsed = Math.max(widthUsed, lineWidthUsed);
        }

        int width = widthUsed;
        int height = maxHeight + heightUsed;

        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0),
                resolveSizeAndState(height, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
            Rect childBound = childrensBounds[index];
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

package com.demo.hencoder.day12;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/8/5
 */

public class DragListenerLayout extends ViewGroup {
    private final int ROWS = 3;
    private final int COLUMNS = 2;

    public DragListenerLayout(Context context) {
        super(context);
        init(context);
    }

    public DragListenerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = width / COLUMNS;
        int childHeight = height / ROWS;
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int childLeft = 0;
        int childTop = 0;
        int childRight = getWidth() / COLUMNS;
        int childBottom = getHeight() / ROWS;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(childLeft,childTop,childRight,childBottom);
            childLeft = (i % COLUMNS) * (getWidth() / COLUMNS);
            childTop =  (i % ROWS) * (getHeight() / ROWS);
            childRight = childLeft + (getWidth() / COLUMNS);
            childBottom = childTop + (getHeight() / ROWS);
        }
    }
}

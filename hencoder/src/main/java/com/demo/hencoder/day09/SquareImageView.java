package com.demo.hencoder.day09;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/25
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int min = Math.min(measuredWidth, measuredHeight);

        setMeasuredDimension(min,min);
    }
}

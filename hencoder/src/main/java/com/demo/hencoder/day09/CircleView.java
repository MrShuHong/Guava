package com.demo.hencoder.day09;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.hencoder.Utils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/28
 */

public class CircleView extends View {

    private static final float PADDING = Utils.dip2px(40);
    private static final float RADIUS = Utils.dip2px(100);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float size = (RADIUS + PADDING) * 2;
        setMeasuredDimension(resolveSizeAndState((int) size,widthMeasureSpec,0),
                resolveSizeAndState((int) size,heightMeasureSpec,0));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(RADIUS + PADDING ,RADIUS + PADDING,RADIUS, mPaint);
    }
}

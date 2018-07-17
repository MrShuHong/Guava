package com.demo.hencoder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.core.utils.ScreenUtils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/15
 */

public class PieChart extends View {

    public static final float PADDING = ScreenUtils.dip2px(40);
    private Paint mPaint;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#4ED10C"));
    }

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(PADDING, PADDING,
                getWidth() - PADDING, getHeight() - PADDING);
        mPaint.setColor(Color.parseColor("#4ED10C"));
        canvas.drawArc(rectF, 0, 70, true, mPaint);
        mPaint.setColor(Color.parseColor("#3D85C6"));
        canvas.drawArc(rectF, 70, 40, true, mPaint);
        mPaint.setColor(Color.parseColor("#B4A7D6"));
        int tempAngle = (230 - 110) / 2 + 100;
        int angle = 360 - tempAngle;
        float offestX = (float) (Math.cos(Math.toRadians(angle)) * ScreenUtils.dip2px(10));
        float offestY = - (float) (Math.sin(Math.toRadians(angle)) * ScreenUtils.dip2px(10));
        Log.d("dsh", "dp10 = " +ScreenUtils.dip2px(10) + " ; offestX = " + offestX + " ; offestY = " + offestY);

        rectF.offset(offestX,offestY);
        canvas.drawArc(rectF, 110, 120, true, mPaint);
        mPaint.setColor(Color.parseColor("#E06666"));
        canvas.drawArc(rectF, 230, 70, true, mPaint);
        mPaint.setColor(Color.parseColor("#434343"));
        canvas.drawArc(rectF, 300, 60, true, mPaint);
    }
}

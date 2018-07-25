package com.demo.hencoder.day06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
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

public class Dashboard extends View {

    public static final float PADDING = ScreenUtils.dip2px(40);

    private Paint mPaint;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(3));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#E06666"));
        mPaint.setStrokeJoin(Paint.Join.BEVEL);

    }

    public Dashboard(Context context) {
        super(context);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(PADDING,PADDING,getWidth() - PADDING,getHeight() - PADDING);

        //获取弧线的长度
        Path arcPath = new Path();
        arcPath.addArc(rectF,120,300);
        PathMeasure measure = new PathMeasure(arcPath,false);
        float advance = (measure.getLength() - ScreenUtils.dip2px(4)) / 20;

        Log.d("dsh","advance = "+advance);
        Path path = new Path();
        path.addRect(0,0,ScreenUtils.dip2px(2),ScreenUtils.dip2px(10), Path.Direction.CCW);
        @SuppressLint("DrawAllocation")
        PathDashPathEffect effect = new PathDashPathEffect(path, advance, 0, PathDashPathEffect.Style.ROTATE);
        mPaint.setPathEffect(effect);
        canvas.drawArc(rectF,120,300,false,mPaint);
        mPaint.setPathEffect(null);

        float advance2 = (measure.getLength() -ScreenUtils.dip2px(4)) / 5;
        path.addRect(0,0,ScreenUtils.dip2px(4),ScreenUtils.dip2px(20), Path.Direction.CCW);
        @SuppressLint("DrawAllocation")
        PathDashPathEffect effect2 = new PathDashPathEffect(path, advance2, 0, PathDashPathEffect.Style.ROTATE);
        mPaint.setPathEffect(effect2);
        canvas.drawArc(rectF,120,300,false,mPaint);
        mPaint.setPathEffect(null);

        canvas.drawArc(rectF,120,300,false,mPaint);
    }
}

package com.demo.hencoder.day06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    public static int[] angles = {70, 40, 120, 70, 60};
    public static String[] colors = {"#4ED10C", "#3D85C6", "#B4A7D6", "#E06666", "#434343"};

    private Paint mPaint;
    private float mEventX;
    private float mEventY;
    private boolean mIsClickDraw;
    private int mLastTiltAngle = 361;

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


        float startAngle = 0;


        for (int index = 0; index < angles.length; index++) {
            @SuppressLint("DrawAllocation")
            RectF rectF = new RectF(PADDING, PADDING,
                    getWidth() - PADDING, getHeight() - PADDING);
            if (mIsClickDraw) {

                //获取图形的中心在屏幕的位置
                int centerX = (int) getWidth() / 2 + getLeft();
                int centerY = (int) getHeight() / 2 + getTop();


                //点击位置与圆心所在角度（顺时针角度）
                int tiltAngle = 360 - getTwoPointAngle(centerX, centerY, mEventX, mEventY);
                double endAngle = (startAngle + angles[index]);
                double computeAngle = 0;

                if (tiltAngle > startAngle && tiltAngle < endAngle) {
                    //并且遇上一次点击的位置不在同一个扇形区域
                    if (!(mLastTiltAngle > startAngle && mLastTiltAngle < endAngle) ){
                        computeAngle = 360 - startAngle - angles[index] / 2;
                        float offestX = (float) (Math.cos(Math.toRadians(computeAngle)) * ScreenUtils.dip2px(10));
                        float offestY = -(float) (Math.sin(Math.toRadians(computeAngle)) * ScreenUtils.dip2px(10));
                        rectF.offset(offestX, offestY);

                        //记录上一次点击的角度
                        mLastTiltAngle = tiltAngle;
                    }else{
                        //一个永远点不到的区域
                        mLastTiltAngle = 361;
                    }
                    Log.d("dsh", "tiltAngle = " + tiltAngle +
                            " , endAngle = " + endAngle + " , computeAngle = " + computeAngle);
                }

            }
            mPaint.setColor(Color.parseColor(colors[index]));
            canvas.drawArc(rectF, startAngle, angles[index], true, mPaint);
            startAngle += angles[index];
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //获取点击的位置在屏幕中的坐标
                mEventX = event.getRawX();
                mEventY = event.getRawY();

                //获取图形的中心在屏幕的位置
                int centerX = (int) getWidth() / 2 + getLeft();
                int centerY = (int) getHeight() / 2 + getTop();
                double distance = Math.sqrt(Math.pow((mEventX - centerX), 2) + Math.pow((mEventY - centerY), 2));
                if (distance < getWidth() / 2 - PADDING) {
                    mIsClickDraw = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取两个点形成的斜线与数学里的坐标系的X轴的夹角
     *
     * @param centerX
     * @param centerY
     * @param eventX
     * @param eventY
     * @return
     */
    public static int getTwoPointAngle(float centerX, float centerY, float eventX, float eventY) {
        double rotation = 0;
        double tmpDegree = Math.toDegrees(Math.atan((eventY - centerY) / (eventX - centerX)));
        if (eventX > centerX && eventY < centerY) {
            //第一象限
            rotation = -tmpDegree;
        } else if (eventX < centerX && eventY < centerY) {
            //第二象限
            rotation = 180 - tmpDegree;
        } else if (eventX < centerX && eventY > centerY) {
            //第三象限
            rotation = 180 - tmpDegree;
        } else if (eventX > centerX && eventY > centerY) {
            //第四象限
            rotation = 360 - tmpDegree;
        } else if (eventX == centerX && eventY < centerY) {
            rotation = 90;
        } else if (eventX == centerX && eventY > centerY) {
            rotation = 270;
        } else if (eventX > centerX && eventY == centerY) {
            rotation = 0;
        } else if (eventX < centerX && eventY == centerY) {
            rotation = 180;
        }
        Log.d("dsh", "rotation = " + rotation);
        return (int) rotation;
    }

}

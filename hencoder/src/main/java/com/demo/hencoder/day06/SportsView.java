package com.demo.hencoder.day06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.core.utils.ScreenUtils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/16
 */

public class SportsView extends View {

    private static final float PADDING = ScreenUtils.dip2px(40);
    private static final float RADIUS = ScreenUtils.dip2px(100);
    private Paint mPaint;

    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(10));
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
        canvas.drawArc(rectF, -60, 230, false, mPaint);



        methodOne(canvas);

       // methodTwo(canvas);

        //画中心点
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(getWidth() / 2, getHeight() / 2, paint);

    }

    private void methodTwo(Canvas canvas) {

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(TypedValue.
                applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
                        Resources.getSystem().getDisplayMetrics()));

        String words = "一个好好学生";
        Rect rect = new Rect();
        textPaint.getTextBounds(words, 0, words.length(), rect);
        int width = rect.width();
        int height = rect.height();

        canvas.drawText("一个好好学生", getWidth() / 2 - width / 2, getHeight() / 2 + height / 2, textPaint);
    }

    private void methodOne(Canvas canvas) {
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(TypedValue.
                applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
                        Resources.getSystem().getDisplayMetrics()));
        String words = "一个好学生";
        @SuppressLint("DrawAllocation")
        StaticLayout staticLayout = new StaticLayout(words, textPaint, (int) (RADIUS * 2 - ScreenUtils.dip2px(10) * 3),
                Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        Log.d("dsh", "x = " + staticLayout.getWidth() / 2);
        Log.d("dsh", "y = " + staticLayout.getHeight() / 2);
        canvas.save();
        //canvas.translate(staticLayout.getWidth() / 2, staticLayout.getHeight() / 2);
        canvas.translate(getWidth() / 2 -staticLayout.getWidth() / 2,
                getHeight() / 2 - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
        canvas.restore();
    }

}

package com.demo.hencoder.day06;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.core.utils.ScreenUtils;
import com.demo.hencoder.R;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/16
 */

public class ImageTextView extends View {

    public static final float TEXT_SIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
            Resources.getSystem().getDisplayMetrics());
    public static final float IMAGE_WIDTH = ScreenUtils.dip2px(150);
    public static final float IMAGE_Y_OFFSET = ScreenUtils.dip2px(80);
    private Paint mPaint;

    private static final String words = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
            "do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis " +
            "aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla" +
            " pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt" +
            " mollit anim id est laborum.";
    private Paint.FontMetrics mFontMetrics;
    private float[] measureTextWidth = new float[1];

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    {
        mFontMetrics = new Paint.FontMetrics();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.getFontMetrics(mFontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("dsh", "top = " + mFontMetrics.top);
        Log.d("dsh", "ascent = " + mFontMetrics.ascent);
        Log.d("dsh", "descent = " + mFontMetrics.descent);
        Log.d("dsh", "bottom = " + mFontMetrics.bottom);
        //根据打印结果，top为负值
        int start = 0;
        float y = -mFontMetrics.top;
        float measureWidth = measureTextWidth(y);
        int count = mPaint.breakText(words, 0, words.length(), true, measureWidth, measureTextWidth);
        while (count > 0) {

            canvas.drawText(words, start, start + count, 0, y, mPaint);
            start += count;
            y += mPaint.getFontSpacing();
            measureWidth = measureTextWidth(y);
            count = mPaint.breakText(words, start, words.length(), true, measureWidth, measureTextWidth);
        }
        Bitmap bitmap = getBitmap(R.drawable.timg, IMAGE_WIDTH);
        canvas.drawBitmap(bitmap,getWidth()-IMAGE_WIDTH,IMAGE_Y_OFFSET,mPaint);
    }

    public float measureTextWidth(float y){
        boolean isBitmapIn = y + mFontMetrics.top > IMAGE_Y_OFFSET && y + mFontMetrics.top < IMAGE_Y_OFFSET + IMAGE_WIDTH
                || y + mFontMetrics.bottom > IMAGE_Y_OFFSET && y + mFontMetrics.bottom > IMAGE_Y_OFFSET + IMAGE_WIDTH;
        if (isBitmapIn){
            return getWidth() - IMAGE_WIDTH;
        }
        return getWidth();

    }

    public Bitmap getBitmap(@DrawableRes int drawableID, float width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), drawableID,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = (int)width;
        return BitmapFactory.decodeResource(getResources(), drawableID,options);

    }
}

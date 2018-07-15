package com.demo.hencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.core.utils.ScreenUtils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/15
 */

public class AvatarView extends View {
    public static final float PADDING = ScreenUtils.dip2px(40);
    private Paint mPaint;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = getBitmap(R.drawable.timg, getWidth());
        canvas.drawBitmap(bitmap,0,0,mPaint);
    }

    public Bitmap getBitmap(@DrawableRes int drawableID,int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), drawableID,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), drawableID,options);

    }
}

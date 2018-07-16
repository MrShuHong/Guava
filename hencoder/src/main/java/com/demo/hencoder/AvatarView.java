package com.demo.hencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
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
        mPaint.setColor(Color.parseColor("#ffffff"));
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
        //外层白边
        canvas.drawCircle(getWidth() / 2, getHeight() / 2,getWidth() / 2, mPaint);


        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        int save = canvas.saveLayer(rectF, mPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2,getWidth() / 2 - ScreenUtils.dip2px(5) , mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Bitmap bitmap = getBitmap(R.drawable.timg, (int) (getWidth() - ScreenUtils.dip2px(10)));
        canvas.drawBitmap(bitmap,ScreenUtils.dip2px(5),ScreenUtils.dip2px(5),mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(save);
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

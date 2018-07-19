package com.demo.hencoder.day07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
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
 * @date 2018/7/18
 */

public class TransformView extends View {

    public static final float IMAGE_SIZE = Utils.dip2px(200);
    public static final float OFFSET = Utils.dip2px(80);

    private Bitmap mBitmap;
    private Paint mPaint;
    private Camera mCamera = new Camera();

    public TransformView(Context context) {
        super(context, null);
    }

    public TransformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TransformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = Utils.getBitmap(getResources(), IMAGE_SIZE);
        mCamera.rotateX(-45);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //先切图片的上半部分
        canvas.clipRect(OFFSET - Utils.dip2px(100), OFFSET, OFFSET + IMAGE_SIZE + Utils.dip2px(100), OFFSET + IMAGE_SIZE / 2);
        //平移画布，使得一整张图片的中心在坐标系的原点
        canvas.translate(OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE / 2);
        //将旋转后的camera投影到canvas
        mCamera.applyToCanvas(canvas);
        //将画布恢复回来
        canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
        canvas.drawBitmap(mBitmap, OFFSET, OFFSET, mPaint);
        canvas.restore();

        canvas.clipRect(0, OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE, OFFSET + IMAGE_SIZE);
        canvas.drawBitmap(mBitmap, OFFSET, OFFSET, mPaint);


    }
}

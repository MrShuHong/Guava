package com.demo.hencoder.day07;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
    public static final float SHADE = Utils.dip2px(100);

    private Bitmap mBitmap;
    private Paint mPaint;
    private Camera mCamera = new Camera();
    private float defualtAngle = 30;
    private float rotationAngle;
    private ObjectAnimator mAnimator;

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

        ObjectAnimator animator = getRotationAnimator();
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //setRotationAngle(0);
            }
        });
    }

    @NonNull
    private ObjectAnimator getRotationAnimator() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(TransformView.this,
                    "rotationAngle", 2 * defualtAngle + 270);
            mAnimator.setDuration(3000);
        }
        return mAnimator;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(OFFSET + IMAGE_SIZE / 2, 0, OFFSET + IMAGE_SIZE / 2, Utils.dip2px(400), mPaint);
        if (rotationAngle <= defualtAngle) {

            mCamera.save();
            mCamera.rotateY(-rotationAngle);
            canvas.save();
            canvas.clipRect(OFFSET + IMAGE_SIZE / 2, OFFSET - SHADE, OFFSET + IMAGE_SIZE + SHADE, OFFSET + IMAGE_SIZE + SHADE);
            canvas.translate(OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE / 2);
            mCamera.applyToCanvas(canvas);
            canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
            canvas.drawBitmap(mBitmap, OFFSET, OFFSET, mPaint);
            canvas.restore();

            mCamera.restore();

            canvas.save();
            canvas.clipRect(OFFSET, OFFSET, OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE);
            canvas.drawBitmap(mBitmap, OFFSET, OFFSET, mPaint);
            canvas.restore();
        } else if (rotationAngle > defualtAngle && rotationAngle - defualtAngle <= 270) {
            float angle = -(rotationAngle - defualtAngle);
            mCamera.save();
            canvas.save();
            canvas.translate((OFFSET + IMAGE_SIZE / 2), (OFFSET + IMAGE_SIZE / 2));
            canvas.rotate(angle);
            //canvas.drawRect(0,-getHeight()/2,getWidth()/2,getHeight(),mPaint);
            canvas.clipRect(0, -getHeight() / 2, getWidth() / 2, getHeight());
            mCamera.rotateY(-defualtAngle);
            mCamera.applyToCanvas(canvas);
            mCamera.restore();
            canvas.rotate(-angle);
            canvas.drawBitmap(mBitmap, -IMAGE_SIZE / 2, -IMAGE_SIZE / 2, mPaint);
            canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
            canvas.restore();

            canvas.save();
            canvas.translate((OFFSET + IMAGE_SIZE / 2), (OFFSET + IMAGE_SIZE / 2));
            canvas.rotate(angle);
            canvas.clipRect(-getWidth(), -getHeight(), 0, getHeight());
            canvas.rotate(-angle);
            canvas.drawBitmap(mBitmap, -IMAGE_SIZE / 2, -IMAGE_SIZE / 2, mPaint);
            canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
            canvas.restore();
        } else if (rotationAngle > defualtAngle + 270 && rotationAngle <= 2 * defualtAngle + 270) {
            float angle = rotationAngle - defualtAngle - 270;
            Log.d("dsh","angle = "+angle);
            mCamera.save();
            canvas.save();
            canvas.translate(OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE / 2);
            //canvas.drawRect(-getWidth() / 2, -getHeight(), getWidth() / 2, 0,mPaint);
            canvas.clipRect(-getWidth() / 2, -getHeight(), getWidth() / 2, 0);
            mCamera.rotateX(-angle);
            mCamera.applyToCanvas(canvas);
            canvas.drawBitmap(mBitmap, -IMAGE_SIZE / 2, -IMAGE_SIZE / 2, mPaint);
            canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
            mCamera.restore();
            canvas.restore();


            mCamera.save();
            canvas.save();
            canvas.translate(OFFSET + IMAGE_SIZE / 2, OFFSET + IMAGE_SIZE / 2);
            canvas.clipRect(-getWidth() / 2, 0, getWidth() / 2, getHeight() / 2);
            mCamera.rotateX(defualtAngle);
            mCamera.applyToCanvas(canvas);
            canvas.drawBitmap(mBitmap, -IMAGE_SIZE / 2, -IMAGE_SIZE / 2, mPaint);
            canvas.translate(-(OFFSET + IMAGE_SIZE / 2), -(OFFSET + IMAGE_SIZE / 2));
            canvas.restore();
            mCamera.restore();
        }

    }
}

package com.demo.hencoder.day10;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.demo.hencoder.R;
import com.demo.hencoder.Utils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/28
 */

public class ScalableImageView extends View {

    public static final float IMAGE_SIZE = Utils.dip2px(200);
    private int mImageWidth;
    private int mImageHeight;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float smallScale, bigScale;
    private GestureDetector mGestureDetector;
    private float mOriginalOffestX;
    private float mOriginalOffestY;
    private float mOffestX;
    private float mOffestY;

    private boolean big = false;
    private float mScale;
    private float scaleFration;
    private ObjectAnimator mScaleAnimator;
    private OverScroller mOverScroller;

    public ScalableImageView(Context context) {
        super(context);
        init(context);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBitmap = Utils.getBitmap(context, R.drawable.timg, IMAGE_SIZE);
        mImageWidth = (int) IMAGE_SIZE;
        mImageHeight = mBitmap.getHeight();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGestureDetector = new GestureDetector(context, new HongGestureListener());
        mGestureDetector.setOnDoubleTapListener(new HongDoubleTapListener());

        mOverScroller = new OverScroller(context);
    }

    public float getScaleFration() {
        return scaleFration;
    }

    public void setScaleFration(float scaleFration) {
        this.scaleFration = scaleFration;
        invalidate();
    }

    private ObjectAnimator getAnimator() {
        if (mScaleAnimator == null) {
            mScaleAnimator = ObjectAnimator.ofFloat(this, "scaleFration", 0f, 1f);
            mScaleAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {
                    super.onAnimationEnd(animation);
                    if (isReverse) {
                        mOffestX = mOffestY = 0;
                    }
                }
            });
        }

        return mScaleAnimator;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mOriginalOffestX = (getWidth() - mImageWidth) / 2;
        mOriginalOffestY = (getHeight() - mImageHeight) / 2;

        if (mImageWidth / mImageHeight > getWidth() / getHeight()) {
            smallScale = (float) getWidth() / mImageWidth;
            bigScale = (float) getHeight() / mImageHeight * 2;
        } else {
            smallScale = (float) getHeight() / mImageHeight;
            bigScale = (float) getWidth() / mImageWidth * 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.translate(mOffestX * scaleFration, mOffestY * scaleFration);
        mScale = smallScale + (bigScale - smallScale) * scaleFration;
        Log.d("dsh", mScale + "");
        canvas.scale(mScale, mScale, getWidth() / 2, getHeight() / 2);
        canvas.translate(mOriginalOffestX, mOriginalOffestY);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    class HongGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY) {
            if (big) {
                mOffestX -= distanceX;
                mOffestX = Math.min(mOffestX, (mImageWidth * bigScale - getWidth()) / 2);
                mOffestX = Math.max(mOffestX, -(mImageWidth * bigScale - getWidth()) / 2);
                mOffestY -= distanceY;
                mOffestY = Math.min(mOffestY, (mImageHeight * bigScale - getHeight()) / 2);
                mOffestY = Math.max(mOffestY, -(mImageHeight * bigScale - getHeight()) / 2);
                invalidate();
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int startX = (int) mOffestX;
            int startY = (int) mOffestY;
            int minX = (int) ((getWidth() - mImageWidth * bigScale) / 2);
            int maxX = -(int) ((getWidth() - mImageWidth * bigScale) / 2);
            int minY = (int) ((getHeight() - mImageHeight * bigScale) / 2);
            int maxY = -(int) ((getHeight() - mImageHeight * bigScale) / 2);
            int over = (int) Utils.dip2px(50);
            mOverScroller.fling(startX, startY, (int) velocityX, (int) velocityY, minX, maxX, minY, maxY, over, over);

            postOnAnimation(new HongRunnable());
            return false;
        }
    }

    class HongDoubleTapListener implements GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                getAnimator().start();
            } else {
                getAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {

            return false;
        }
    }

    class HongRunnable implements Runnable {

        @Override
        public void run() {
            if (mOverScroller.computeScrollOffset()) {
                mOffestX = mOverScroller.getCurrX();
                mOffestY = mOverScroller.getCurrY();
                invalidate();
                postOnAnimation(new HongRunnable());
            }

        }
    }
}

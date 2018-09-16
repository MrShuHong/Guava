package com.demo.hencoder.day12;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/8/5
 */

public class MyTwoPager extends ViewGroup {

    private ViewConfiguration mViewConfiguration;
    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;
    private int mMinVelocity;
    private float mDownX;
    private float mDownY;
    private float mScrollX;
    private boolean mScroling = false;
    private OverScroller mOverScroller;

    public MyTwoPager(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mViewConfiguration = ViewConfiguration.get(context);
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();
        mMinVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();

        mOverScroller = new OverScroller(context);
    }

    public MyTwoPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = 0;
        int childTop = top;
        int childRight = getWidth();
        int childBottom = bottom;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childRight, childBottom);
            childLeft += getWidth();
            childRight += getWidth();
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(ev);
        boolean result = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = (int) (mDownX - ev.getX());
                int distanceY = (int) (mDownY - ev.getY());
                if (!mScroling) {
                    if (Math.abs(distanceX) > Math.abs(distanceY)
                            && Math.abs(distanceX) > mViewConfiguration.getScaledPagingTouchSlop()) {
                        //请求父view不要在拦截这个一个事件序列「
                        getParent().requestDisallowInterceptTouchEvent(true);
                        result = true;
                        mScroling = true;
                    }
                }

                break;
            default:
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = (int) (mDownX - event.getX() + mScrollX);
                if (distanceX > getWidth() * getChildCount()) {
                    distanceX = getWidth() * getChildCount();
                } else if (distanceX < 0) {
                    distanceX = 0;
                }
                scrollTo(distanceX, 0);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                float xVelocity = mVelocityTracker.getXVelocity();
                int targetPage = 0;
                int scrollX = getScrollX();
                if (Math.abs(xVelocity) >= mMinVelocity) {
                    //根据方向判断是那一页
                    targetPage = xVelocity < 0 ? 1 : 0;
                } else {
                    //判断是否超过一半
                    targetPage = scrollX > getWidth() / 2 ? 1 : 0;
                }
                //如果目标是第二页，需要滑动getWidth() - scrollX ， 如果是回到当前页，就需要往回滑动scrollX
                int distance = targetPage == 1 ? (getWidth() - scrollX) : -scrollX;
                mOverScroller.startScroll(scrollX, 0, distance, 0);
                postInvalidateOnAnimation();
                break;
            default:
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //If it returns true, the animation is not yet finished.
        boolean isFinished = mOverScroller.computeScrollOffset();
        if (isFinished){
            scrollTo(mOverScroller.getCurrX(),mOverScroller.getCurrY());
            postInvalidateOnAnimation();
        }

    }
}

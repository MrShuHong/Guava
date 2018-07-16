package com.example.behavior.lesson02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2018/5/18.
 */

public class ScaleUpShowBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "ScaleUpShowBehavior";

    private boolean isAnimatingOut = false;

    //构造必须有
    public ScaleUpShowBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target,
                                       int nestedScrollAxes,
                                       int type) {


        Logger.d("ViewCompat.SCROLL_AXIS_VERTICAL = "
                + ViewCompat.SCROLL_AXIS_VERTICAL
                + "nestedScrollAxes = " + nestedScrollAxes);
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
        //return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed,
                               int type) {

        Logger.d("dxConsumed = " + dxConsumed
                + " ; dyConsumed = " + dyConsumed
                + " ; dxUnconsumed = " + dxUnconsumed
                + " ; dyUnconsumed = " + dyUnconsumed);

        Logger.d("child.getVisibility() = " + child.getVisibility());
        //dxConsumed = 0 ; dyConsumed = -1 ; dxUnconsumed = 0 ; dyUnconsumed = 0
        if (dyConsumed > 0 && dxUnconsumed == 0){
            //上滑中
            Logger.d("   上滑中  ");
        }

        if (dyConsumed < 0 && dxUnconsumed == 0){
            //下滑中
            Logger.d("   下滑中  ");
        }

        if (dyConsumed == 0 && dxUnconsumed < 0){
            //到达上边界了
            Logger.d("   到达上边界了  ");
        }

        if (dyConsumed == 0 && dxUnconsumed > 0){
            //到达下边界了
            Logger.d("   到达下边界了  ");
        }

        if ((dyConsumed < 0 || dyUnconsumed < 0)
                && child.getVisibility() == View.VISIBLE
                && !isAnimatingOut) {// 显示
            AnimatorUtil.scaleHide(child, viewPropertyAnimatorListener);
        }

        if ((dyConsumed < 0 || dyUnconsumed < 0)
                && child.getVisibility() != View.VISIBLE ) {
            AnimatorUtil.scaleHide(child, null);
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                   @NonNull FloatingActionButton child,
                                   @NonNull View target,
                                   int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }



    ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };
}

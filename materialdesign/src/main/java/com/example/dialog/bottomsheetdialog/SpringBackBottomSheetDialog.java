package com.example.dialog.bottomsheetdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.statusbar.R;

/**
 * Created by admin on 2018/5/7.
 * 可回弹的BottomSheetDialog  , 具体效果可以看网易云音乐的 播放列表
 */
public class SpringBackBottomSheetDialog extends AppCompatDialog {
    private boolean mCancelable;
    private ConstraintLayout mConstraintLayout;
    private BottomSheetBehavior<FrameLayout> mSheetBehavior;

    public SpringBackBottomSheetDialog(@NonNull Context context) {
        this(context, getThemeResId(context, 0));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    public SpringBackBottomSheetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public SpringBackBottomSheetDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mCancelable = cancelable;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSheetBehavior != null){
            mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        final FrameLayout container = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialog_spring_back_bottom_sheet_layout, null);
        mConstraintLayout = container.findViewById(R.id.constraint_layout);
        if (layoutResId !=  0 && view == null){
            view = LayoutInflater.from(getContext()).inflate(layoutResId, null);
        }

        //dialog 真正的填充数据的 container
        FrameLayout bottomSheetContainer = mConstraintLayout.findViewById(R.id.frame_container_layout);

        mSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer);
        mSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
        mSheetBehavior.setHideable(mCancelable);

        if (params != null){
            bottomSheetContainer.addView(view,params);
        }else{
            bottomSheetContainer.addView(view);
        }
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(bottomSheetContainer, new AccessibilityDelegateCompat() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host,
                                                          AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (mCancelable) {
                    info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
                    info.setDismissable(true);
                } else {
                    info.setDismissable(false);
                }
            }

            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && mCancelable) {
                    cancel();
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        });

        /*bottomSheetContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // coordinator intecept, 这没用
                return true;
            }
        });*/
        return container;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(
                    android.support.design.R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our light theme
                themeId = android.support.design.R.style.Theme_Design_Light_BottomSheetDialog;
            }
        }
        return themeId;
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                cancel();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}

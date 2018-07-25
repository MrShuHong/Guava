package com.demo.hencoder.day08;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import com.demo.hencoder.R;
import com.demo.hencoder.Utils;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/22
 */

public class MaterialEditText extends AppCompatEditText {

    private static final String TAG = "MaterialEditText";
    private static final float LABEL_SIZE = Utils.dip2px(16);
    private static final float LABEL_OFFSET_Y = Utils.dip2px(8);
    private static final float LABEL_OFFSET_X = Utils.dip2px(4);
    private  int leftDrawbleId;
    private boolean useLableModle = false;
    private boolean isShownLabel = false;
    private Paint mPaint;
    /**
     * label 动画显示的百分比
     */
    private float labelFraction;
    private ObjectAnimator mAnimator;
    private ObjectAnimator mAnimatorReverse;
    private int mColorAccent;
    /*private Bitmap mLeftBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mLeftWidth = 0;*/

    public MaterialEditText(Context context) {
        this(context, null);
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArrayColorAccent = context.obtainStyledAttributes(attrs, new int[]{R.attr.colorAccent});
        mColorAccent = typedArrayColorAccent.getColor(0, Color.BLACK);
        typedArrayColorAccent.recycle();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        this.useLableModle = typedArray.getBoolean(R.styleable.MaterialEditText_useLableModle, false);
        this.leftDrawbleId = typedArray.getResourceId(R.styleable.MaterialEditText_leftDrawble, 0);
        typedArray.recycle();

        if (useLableModle) {
            setPadding(getPaddingLeft(), (int) (getPaddingTop() + LABEL_SIZE + LABEL_OFFSET_Y),
                    getPaddingRight(), getPaddingBottom());
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(LABEL_SIZE);

        setBackground(null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "s.length() = " + s.length() + " ; isShownLabel = " + isShownLabel);
                if (s.length() > 0 && !isShownLabel) {
                    isShownLabel = true;
                    Log.d(TAG, "执行动画啦");
                    getAnimator().start();

                } else if (s.length() == 0 && isShownLabel) {
                    Log.d(TAG, "执行反向动画啦");

                    //反过来执行
                    getAnimatorReverse().start();
                    getAnimatorReverse().addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isShownLabel = false;
                        }
                    });
                }
            }
        });
    }

    public boolean isUseLableModle() {
        return useLableModle;
    }

    /**
     * @param useLableModle
     */
    public void setUseLableModle(boolean useLableModle) {
        if (this.useLableModle != useLableModle) {
            if (useLableModle) {
                setPadding(getPaddingLeft(), (int) (getPaddingTop() + LABEL_SIZE + LABEL_OFFSET_Y),
                        getPaddingRight(), getPaddingBottom());
            } else {
                if (this.useLableModle) {
                    Log.d(TAG,"setUseLableModle "+useLableModle);
                    setPadding(getPaddingLeft(), (int) (getPaddingTop() - LABEL_SIZE - LABEL_OFFSET_Y),
                            getPaddingRight(), getPaddingBottom());
                }else{
                    setPadding(getPaddingLeft(), getPaddingTop(),getPaddingRight(), getPaddingBottom());
                }
            }
        }
        this.useLableModle = useLableModle;
        invalidate();
    }

    public float getLabelFraction() {
        return labelFraction;
    }

    public void setLabelFraction(float labelFraction) {
        this.labelFraction = labelFraction;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*if (leftDrawbleId != 0){
            if (mLeftBitmap == null){
                float imageSize = (getHeight() - LABEL_SIZE - LABEL_OFFSET_Y ) ;
                mLeftBitmap = Utils.getBitmap(getContext(), leftDrawbleId, imageSize);
            }

            mBitmapWidth = mLeftBitmap.getWidth();
            mBitmapHeight = mLeftBitmap.getHeight();
            if (mBitmapWidth > 0 && mBitmapHeight > 0){
                mLeftWidth = mBitmapWidth;
            }

            if(mLeftWidth > 0){
                canvas.drawBitmap(mLeftBitmap,getPaddingLeft(),getPaddingTop(),mPaint);
            }

        }*/



        if (isShownLabel && useLableModle) {
            Log.d(TAG, "getPaddingLeft() = " + getPaddingLeft() );
            Log.d(TAG, "getPaddingTop() = " + getPaddingTop() );

            int alpha = mPaint.getAlpha();
            mPaint.setColor(getCurrentTextColor());
            mPaint.setAlpha((int) (labelFraction * 0xff));
            //模仿从下滑动上去的效果
            float imitateOffset = (1 - labelFraction) * getTextSize();
            canvas.drawText(getHint(), 0, getHint().length(), LABEL_OFFSET_X , LABEL_OFFSET_Y + LABEL_SIZE + imitateOffset, mPaint);
            mPaint.setAlpha(alpha);
        }

        if (hasFocus()) {
            mPaint.setColor(mColorAccent);
            mPaint.setStrokeWidth(Utils.dip2px(2));
        } else {
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(Utils.dip2px(0.75f));
        }
        canvas.drawLine(LABEL_OFFSET_X,
                getBottom() - Utils.dip2px(8),
                getWidth() - LABEL_OFFSET_X,
                getBottom() - Utils.dip2px(8), mPaint);
    }


    private ObjectAnimator getAnimator() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "labelFraction", 0, 1);
        }
        mAnimator.setDuration(300);
        return mAnimator;
    }

    private ObjectAnimator getAnimatorReverse() {
        if (mAnimatorReverse == null) {
            mAnimatorReverse = ObjectAnimator.ofFloat(this, "labelFraction", 1, 0);
        }
        mAnimatorReverse.setDuration(300);
        return mAnimatorReverse;
    }

}

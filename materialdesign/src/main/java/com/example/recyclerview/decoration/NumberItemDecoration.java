package com.example.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 参考 https://www.jianshu.com/p/9a796bb23a47
 */
public class NumberItemDecoration extends RecyclerView.ItemDecoration {

    private int dividerWidth;
    private int leftPadding;
    private int topPadding;
    private int bottomPadding;
    private int rightPadding;
    private int dividerColor;
    private Paint mPaint;
    private Paint mCirclePaint;
    private final Paint mTextPaint;

    public NumberItemDecoration(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#eeeeee"));

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(50);
        mTextPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = parent.getChildAt(index);
            Rect rect = new Rect(0,child.getTop() ,child.getLeft(),child.getBottom());
            // 画圆
            canvas.drawCircle(rect.centerX(), rect.centerY(), 40, mCirclePaint);

            // 1, 画文字
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float font_top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float font_bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
            int baseLineY = (int) (rect.centerY() - font_top/2 - font_bottom/2);//基线中间点的y轴计算公式

            canvas.drawText(index+"",rect.centerX(),baseLineY,mTextPaint);

            // 2, 画分割线
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + dividerWidth;
            canvas.drawRect(child.getLeft() + leftPadding, top, child.getRight() - rightPadding, bottom, mPaint);
        }

    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(200, 0, 0, dividerWidth);
            }
        }

    }

}

package com.example.widget;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by admin on 2018/8/29.
 */

public class VerticalLayoutManager extends RecyclerView.LayoutManager {
    /**
     * 用于保存item的位置信息
     */
    private SparseArray<Rect> allItemRects = new SparseArray<>();
    /**
     * 用于保存item是否处于可见状态的信息
     */
    private SparseBooleanArray itemStates = new SparseBooleanArray();

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    private int totalHeight = 0;
    /**
     * 纵向的滑动偏移量
     */
    private int verticalScrollOffset = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        super.onLayoutChildren(recycler, state);

        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i);
            addView(scrap);

            measureChildWithMargins(scrap, 0, 0);

            int perItemWidth = getDecoratedMeasuredWidth(scrap);
            int perItemHeight = getDecoratedMeasuredHeight(scrap);

            layoutDecorated(scrap, 0, offsetY, perItemWidth, offsetY + perItemHeight);
            offsetY += perItemHeight;
        }

        /*if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }

        //detachAndScrapAttachedViews(recycler);
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View childView = recycler.getViewForPosition(i);
            addView(childView);
            measureChildWithMargins(childView, 0, 0);
            //重新调整childview 的大小，去除ItemDecoration，并设置到Rect
            calculateItemDecorationsForChild(childView, new Rect());

            int width = getDecoratedMeasuredWidth(childView);
            int height = getDecoratedMeasuredHeight(childView);

            Rect rect = allItemRects.get(i);
            if (rect == null){
                rect = new Rect();
            }
            rect.set(0,totalHeight,screenWidth,totalHeight+height);
            //设置item的显示区域
            layoutDecorated(childView, 0, totalHeight, screenWidth, totalHeight + height);

            totalHeight += height;

            *//*allItemRects.put(i,rect);
            itemStates.put(i,false);*//*
        }*/

        //recycleAndFillView(recycler, state);
    }

    /*private void recycleAndFillView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }

        //获取此时屏幕的显示区域
        Rect displayRect = new Rect(0, verticalScrollOffset,
                getHorizontallySpace(), verticalScrollOffset + getVerticallySpace());
        //将不显示在屏幕上的item移除，保存到recycle的缓存中
        Rect childRect = new Rect();
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            //获取并保存每个子view的位置信息，包括ItemDecorator
            childRect.left = getDecoratedLeft(child);
            childRect.top = getDecoratedTop(child);
            childRect.right = getDecoratedRight(child);
            childRect.bottom = getDecoratedBottom(child);

            //如果得到的子view的位置不在屏幕的显示区域
            if (!Rect.intersects(displayRect,childRect)){
                //移除并回收view
                removeAndRecycleView(child,recycler);
                itemStates.put(i,false);
            }
        }

        //计算显示在屏幕中的view
        for (int i=0; i < getItemCount(); i++){
            if (Rect.intersects(displayRect,allItemRects.get(i))){
                View itemView = recycler.getViewForPosition(i);
                measureChildWithMargins(itemView,0,0);
                addView(itemView);
                Rect itemRect = allItemRects.get(i);
                layoutDecoratedWithMargins(itemView,
                        itemRect.left,
                        itemRect.top - verticalScrollOffset,
                        itemRect.right,
                        itemRect.bottom - verticalScrollOffset);

                itemStates.put(i, true); //更新该View的状态为依附
            }
        }
    }


    private int getVerticallySpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontallySpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);
        //实际要滑动的距离
        int travel = dy;
        if (verticalScrollOffset + dy < 0){
            travel = -verticalScrollOffset;
        }else if (verticalScrollOffset + dy > totalHeight - getVerticallySpace()){
            travel = totalHeight - getVerticallySpace() - verticalScrollOffset;
        }


        *//*if (verticalScrollOffset + dy < 0) {
            verticalScrollOffset = 0;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticallySpace()) {
            verticalScrollOffset = totalHeight - getVerticallySpace();
        } else {
            verticalScrollOffset += travel;
        }*//*

        // 调用该方法通知view在y方向上移动指定距离
        offsetChildrenVertical(-travel);

        //recycleAndFillView(recycler,state);

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;
        return travel;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //在这个方法中处理水平滑动
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }*/
}

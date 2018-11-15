package com.example.recyclerview.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.core.utils.ScreenUtils;
import com.example.recyclerview.adapter.QQItemMoveAdapter;
import com.example.statusbar.R;

public class QQItemMoveCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "QQItemMoveCallback";
    private float mSwipeDx;
    private int mItemRight;
    private float mLastdX;

    /*private ItemTouchMoveListener itemTouchMoveListener;

    public QQItemMoveCallback(ItemTouchMoveListener itemTouchMoveListener) {
        this.itemTouchMoveListener = itemTouchMoveListener;
    }*/

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        int result = makeMovementFlags(dragFlags, swipeFlags);
        return result;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcViewHolder,
                          RecyclerView.ViewHolder targetViewHolder) {

        /*boolean result = itemTouchMoveListener.onItemMove(srcViewHolder.getAdapterPosition(),
                targetViewHolder.getAdapterPosition());*/
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //itemTouchMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        Log.d(TAG, "clearView: mItemRight = " + mItemRight);
       /* QQItemMoveAdapter.ItemViewHolder itemViewHolder = (QQItemMoveAdapter.ItemViewHolder) viewHolder;
        if (isDelete){
            itemViewHolder.mItemContent.setTranslationX(-ScreenUtils.dip2px(90));
        }else {
            super.clearView(recyclerView, viewHolder);
        }*/
        /*if (mItemRight == ScreenUtils.dip2px(90)) {
            isShowDelete = false;
        }
        if (mItemRight == 0) {
            isShowDelete = true;
        }*/

        super.clearView(recyclerView, viewHolder);
    }

    boolean isShowDelete = false;

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        /*//方案一
        QQItemMoveAdapter.ItemViewHolder itemViewHolder = (QQItemMoveAdapter.ItemViewHolder) viewHolder;
        int width = itemViewHolder.itemView.getWidth();
        Log.d(TAG, "onChildDraw: dX = " + dX + "  ;width = " + width + "  ; isShowDelete = " + isShowDelete);
        if (dX - mLastdX > 0) {
            //代表在右滑
            if (isShowDelete) {
                float rightdX = -ScreenUtils.dip2px(90) + dX;
                if (rightdX <= 0){
                    itemViewHolder.mItemContent.setTranslationX(-ScreenUtils.dip2px(90) + dX);
                }else{
                    itemViewHolder.mItemContent.setTranslationX(0);
                    isShowDelete = false;
                }
            }
        } else {
            //代表在左滑
            if (dX < -ScreenUtils.dip2px(90)) {
                itemViewHolder.mItemContent.setTranslationX(-ScreenUtils.dip2px(90));
                isShowDelete = true;
            }else{
                itemViewHolder.mItemContent.setTranslationX(dX);
            }
        }

        mLastdX = dX;*/


       // super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        //方案二

        QQItemMoveAdapter.ItemViewHolder itemViewHolder = (QQItemMoveAdapter.ItemViewHolder) viewHolder;
        float actionWidth = itemViewHolder.mLlSlide.getWidth();
        if (dX < -actionWidth) {
            dX = -actionWidth;
        }
        itemViewHolder.mItemContent.setTranslationX(dX);
        return;

    }
}

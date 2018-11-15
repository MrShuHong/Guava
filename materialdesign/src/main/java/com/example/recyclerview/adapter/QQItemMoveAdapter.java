package com.example.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recyclerview.helper.ItemTouchMoveListener;
import com.example.statusbar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QQItemMoveAdapter extends RecyclerView.Adapter {

    private List<String> contents = new ArrayList<>();


    public QQItemMoveAdapter(List<String> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_layout, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String content = contents.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.txtItem.setText(content);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public final TextView txtItem;
        public final RelativeLayout mItemContent;
        public final LinearLayout mLlSlide;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txt_item);
            mItemContent = itemView.findViewById(R.id.item_content);
            mLlSlide = itemView.findViewById(R.id.ll_slide);
        }
    }
}

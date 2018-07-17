package com.example.behavior.lesson01.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.statusbar.R;

import java.util.List;

/**
 * Created by admin on 2018/5/17.
 */

public class TestItemAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final LayoutInflater mInflater;
    private final List<String> list;

    public TestItemAdapter(Context context, List<String> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public TestItemAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_test_layout, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof TestViewHolder) {
            TestViewHolder testViewHolder = (TestViewHolder) holder;
            testViewHolder.mTxt_item.setText(position + "");

            testViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "position = " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 30;
        } else {
            return list.size();
        }

    }

    private class TestViewHolder extends RecyclerView.ViewHolder {


        private final TextView mTxt_item;
        private final View itemView;

        public TestViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mTxt_item = itemView.findViewById(R.id.txt_item);
        }
    }
}

package com.example.widget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.widget.R;
import com.example.widget.bean.VerticalLayoutBean;

import java.util.List;

/**
 * Created by admin on 2018/8/31.
 */

public class DemoAdapter extends RecyclerView.Adapter {

    private List<VerticalLayoutBean> data;

    public DemoAdapter(List<VerticalLayoutBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_layout, parent, false);
        DemoViewHolder demoViewHolder = new DemoViewHolder(inflate);
        return demoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DemoViewHolder demoViewHolder = (DemoViewHolder) holder;
        VerticalLayoutBean bean = data.get(position);
        demoViewHolder.txtContent.setText(bean.getContent());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DemoViewHolder extends RecyclerView.ViewHolder{

        TextView txtContent;
        public DemoViewHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
        }
    }
}

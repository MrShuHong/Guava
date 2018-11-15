package com.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.widget.R;
import com.example.widget.VerticalLayoutManager;
import com.example.widget.adapter.DemoAdapter;
import com.example.widget.adapter.VerticalLayoutAdapter;
import com.example.widget.bean.VerticalLayoutBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/8/31.
 */

public class VerticalLayoutManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_vertical_layout_namager);

        RecyclerView recyclerView = findViewById(R.id.recyler_01);
        recyclerView.setLayoutManager(new VerticalLayoutManager());

        List<VerticalLayoutBean> list = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            VerticalLayoutBean bean =  new VerticalLayoutBean();
            bean.setContent("这是条目"+i);
            list.add(bean);
        }
        VerticalLayoutAdapter verticalLayoutAdapter = new VerticalLayoutAdapter(R.layout.item_vertical_layout,list);

        DemoAdapter demoAdapter = new DemoAdapter(list);
        recyclerView.setAdapter(demoAdapter);

        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
                        .sizeResId(R.dimen.divider)
                        .build());
    }
}

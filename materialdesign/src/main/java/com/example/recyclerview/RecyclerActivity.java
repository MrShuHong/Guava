package com.example.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.core.utils.ScreenUtils;
import com.example.recyclerview.adapter.ItemMoveAdapter;
import com.example.recyclerview.adapter.QQItemMoveAdapter;
import com.example.recyclerview.decoration.CustomItemDecoration;
import com.example.recyclerview.decoration.NumberItemDecoration;
import com.example.recyclerview.helper.CommonItemMoveCallback;
import com.example.recyclerview.helper.QQItemMoveCallback;
import com.example.statusbar.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/material_design/resyclerview")
public class RecyclerActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomItemDecoration itemDecoration = new CustomItemDecoration((int) ScreenUtils.dip2px(1));

        NumberItemDecoration numberItemDecoration = new NumberItemDecoration((int) ScreenUtils.dip2px(1));
        //itemDecoration.setLeftPadding((int) ScreenUtils.dip2px(15));
        //itemDecoration.setRightPadding((int) ScreenUtils.dip2px(15));
        recyclerView.addItemDecoration(numberItemDecoration);

        //上下拖动 和左右滑动删除
        /*List<String> data = DataUtils.createData();
        ItemMoveAdapter itemMoveAdapter = new ItemMoveAdapter(data);
        recyclerView.setAdapter(itemMoveAdapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CommonItemMoveCallback(itemMoveAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);*/


        List<String> data = DataUtils.createData();
        QQItemMoveAdapter itemMoveAdapter = new QQItemMoveAdapter(data);
        recyclerView.setAdapter(itemMoveAdapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new QQItemMoveCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}

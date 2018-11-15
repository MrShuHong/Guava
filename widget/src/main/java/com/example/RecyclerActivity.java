package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.treeview.TreeSelcetActivity;
import com.example.widget.R;

/**
 * Created by admin on 2018/8/31.
 */
@Route(path = "/widget/main")
public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_main);

        findViewById(R.id.btn_recycler_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RecyclerActivity.this,VerticalLayoutManagerActivity.class));
            }
        });

        findViewById(R.id.btn_tree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RecyclerActivity.this,OrgTreeActivity.class));
            }
        });

        findViewById(R.id.btn_tree_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RecyclerActivity.this,TreeSelcetActivity.class));
            }
        });
    }
}

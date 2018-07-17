package com.example.behavior.lesson02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.behavior.lesson01.adapter.TestItemAdapter;
import com.example.statusbar.R;

/**
 * Created by admin on 2018/5/18.
 */
@Route(path = "/material_design/floatingbehavior")
public class CustomFloatingActionButtonActivity extends AppCompatActivity {

    private boolean isInitializeFAB = false;
    private FloatingActionButton mBtnFloating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.behavior_lesson02_custom_floating_button);
        mBtnFloating = findViewById(R.id.btn_floating);

        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, LinearLayout.VERTICAL);

        recycler_view.addItemDecoration(dividerItemDecoration);
        TestItemAdapter testItemAdapter = new TestItemAdapter(this);
        recycler_view.setAdapter(testItemAdapter);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInitializeFAB) {
            isInitializeFAB = true;
            hideFAB();
        }
    }

    private void hideFAB() {
        mBtnFloating.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtil.scaleHide(mBtnFloating, new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                    }
                    @Override
                    public void onAnimationEnd(View view) {
                        mBtnFloating.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationCancel(View view) {
                    }
                });
            }
        }, 500);
    }
}

package com.example.behavior.lesson01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.behavior.lesson01.adapter.TestItemAdapter;
import com.example.statusbar.R;

/**
 * Created by admin on 2018/5/17.
 * 练习使用 BottomSheetBehavior
 */
@Route(path = "/material_design/behavior")
public class BehaviorActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomSheetBehavior<View> mTabLayoutBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.behavior_actiivity_lesson_01);
        View btn_bottomsheet = findViewById(R.id.btn_bottomsheet);
        View bottom_tab_layout = findViewById(R.id.bottom_tab_layout);
        mTabLayoutBehavior = BottomSheetBehavior.from(bottom_tab_layout);

        //使用的重点，按照博客上说的，点击显示隐藏并没有反应，百度一下，发现需要这只下面这行代码
        mTabLayoutBehavior.setPeekHeight(100);//折叠后的高度
        //tab_layout_behavior.setSkipCollapsed(false);
        btn_bottomsheet.setOnClickListener(this);

        findViewById(R.id.btn_bottomsheet_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_bottomsheet) {
            if (mTabLayoutBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mTabLayoutBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//设置成折叠状态
            } else if (mTabLayoutBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mTabLayoutBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//这是成展开状态
            }
        } else if (viewId == R.id.btn_bottomsheet_dialog) {

            if (mBottomSheetDialog == null){
                mBottomSheetDialog = createBottomSheetDialog();
            }
            if (mBottomSheetDialog.isShowing()){
                mBottomSheetDialog.dismiss();
            }else{
                mBottomSheetDialog.show();
            }

        }
    }

    private BottomSheetDialog createBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.behavior_lesson01_bottom_sheet_dialog, null);
        bottomSheetDialog.setContentView(dialogView);
        RecyclerView recycler_view = dialogView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, LinearLayout.VERTICAL);

        recycler_view.addItemDecoration(dividerItemDecoration);
        TestItemAdapter testItemAdapter = new TestItemAdapter(this);
        recycler_view.setAdapter(testItemAdapter);
        return bottomSheetDialog;
    }
}

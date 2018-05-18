package com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.statusbar.R;

/**
 * Created by admin on 2018/5/17.
 */
@Route(path = "/material_design/main")
public class MaterialDesignActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design_main);

        findViewById(R.id.status_bar).setOnClickListener(this);
        findViewById(R.id.bottom_sheet_behavior).setOnClickListener(this);
        findViewById(R.id.custom_bottom_sheet).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.status_bar) {
            ARouter.getInstance().build("/material_design/statusbar").navigation();
        }else if(viewId == R.id.bottom_sheet_behavior){
            ARouter.getInstance().build("/material_design/behavior").navigation();
        }else if (viewId == R.id.custom_bottom_sheet){
            ARouter.getInstance().build("/material_design/custom_bottom_sheet").navigation();
        }
    }
}

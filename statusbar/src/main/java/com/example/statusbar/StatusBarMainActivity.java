package com.example.statusbar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.core.Utils.statusbar.StatusBarUtil;

/**
 * Created by admin on 2018/4/25.
 */

public class StatusBarMainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statusbar_main_layout);

        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimary));
    }
}

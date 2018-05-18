package com.example.statusbar;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.core.utils.statusbar.StatusBarUtil;

/**
 * Created by admin on 2018/4/25.
 */
@Route(path = "/material_design/statusbar")
public class StatusBarMainActivity extends BaseActivity {

    private HandlerThread handlerThread;
    private Handler threadHandler;
    private Handler mainHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statusbar_main_layout);

        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        Log.d("HandlerThread", "当前线程id  == " + Process.myTid() + "");

        mainHandler = new Handler();
        Message obtain = Message.obtain();
        mainHandler.sendMessage(obtain);
        handlerThread = new HandlerThread("TEST");
        handlerThread.start();

        threadHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                try {
                    //模拟耗时
                    Thread.sleep(2000);
                    String result = "    每隔2秒更新一下数据：";
                    result += Math.random();
                    Log.d("HandlerThread", "当前线程id  == " + Process.myTid() + result);
                    /*mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });*/

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadHandler.sendEmptyMessage(101);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}

package com.example.sourcecode.handler;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by admin on 2018/5/3.
 */

public class DownloadIntentService extends IntentService {
    private static final String TAG = "DownloadIntentService";

    public static final String DOWNLOAD_URL = "down_load_url";
    public static final int WHAT_DOWNLOAD_FINISHED = 1;
    public static final int WHAT_DOWNLOAD_STARTED = 2;
    public static Handler mUIHandler;

    public DownloadIntentService(){
        super(TAG);
    }
    public DownloadIntentService(String name) {
        super(name);
    }

    public static void setUIHandler(Handler handler){
        mUIHandler = handler;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String download_url = intent.getStringExtra(DOWNLOAD_URL);
        if (mUIHandler != null){
            mUIHandler.sendMessage(mUIHandler.obtainMessage(WHAT_DOWNLOAD_STARTED));
        }
        //todo 下载图片的代码

        if (mUIHandler != null){
            mUIHandler.sendMessage(mUIHandler.obtainMessage(WHAT_DOWNLOAD_FINISHED));
        }

    }
}

package com.example.sourcecode.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.List;

/**
 * Created by admin on 2018/5/3.
 */

public class DownloadThread extends HandlerThread implements Handler.Callback {

    private Handler mWorkHandler;
    private Handler mUIHandler;
    private List<String> mDownloadUrlList;
    private final String KEY_URL = "url";
    public static final int TYPE_START = 1;
    public static final int TYPE_FINISHED = 2;

    public DownloadThread setDownloadUrlList(List<String> downloadUrlList) {
        mDownloadUrlList = downloadUrlList;
        return this;
    }

    public DownloadThread setUIHandler(Handler UIHandler) {
        mUIHandler = UIHandler;
        return this;
    }

    public DownloadThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        mWorkHandler = new Handler(getLooper(), this);

        if (mUIHandler == null){
            throw new IllegalArgumentException("Not set UIHandler! ");
        }

        if (mDownloadUrlList == null || mDownloadUrlList.size() <= 0){
            throw new IllegalArgumentException("the downloadUrlLsit is null, Please set DownloadUrlList! ");
        }

        for (String url : mDownloadUrlList) {
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL,url);
            obtain.setData(bundle);
            mWorkHandler.sendMessage(obtain);
        }

    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg == null || msg.getData() == null){
            return false;
        }
        String downloadUrl = msg.getData().getString(KEY_URL);
        Message startMessage = mUIHandler.obtainMessage(TYPE_START);
        mUIHandler.sendMessage(startMessage);

        //todo download code

        Message finishMessage = mUIHandler.obtainMessage(TYPE_FINISHED);
        mUIHandler.sendMessage(finishMessage);
        return false;
    }

    @Override
    public boolean quitSafely() {
        mUIHandler = null;
        return super.quitSafely();
    }
}

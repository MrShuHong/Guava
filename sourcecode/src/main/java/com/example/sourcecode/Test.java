package com.example.sourcecode;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

import com.example.sourcecode.handler.DownloadIntentService;

/**
 * Created by admin on 2018/5/3.
 */

public class Test  implements Handler.Callback{

    private void testIntentService(Context context){
        DownloadIntentService.setUIHandler(new Handler(this));

        Intent intent = new Intent(context,DownloadIntentService.class);
        intent.putExtra(DownloadIntentService.DOWNLOAD_URL,"下载url");
        context.startService(intent);

        //LocalBroadcastManager
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

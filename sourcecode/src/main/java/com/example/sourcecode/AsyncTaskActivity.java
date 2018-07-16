package com.example.sourcecode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sourcecode.asynctask.DownloadAsyncTask;
import com.example.sourcecode.handler.DownloadThread;

/**
 * Created by admin on 2018/5/18.
 */

public class AsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute("我是参数1","我是参数2");

    }

//    private static AsyncTask myAsyncTask = new AsyncTask() {
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            return null;
//        }
//    };
}

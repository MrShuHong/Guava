package com.example.sourcecode.asynctask;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by admin on 2018/6/6.
 */

public class DownloadAsyncTask extends AsyncTask {

    private static final String TAG = "DownloadAsyncTask";

    /**
     * 异步任务开始时
     * 工作在主线程
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 异步任务结果的回调
     * 工作在主线程
     *
     * @param o
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        Log.d(TAG, "  onPostExecute " + o.toString());
    }

    /**
     * 异步任务执行的属性
     * 工作在主线程
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    /**
     * 执行异步任务的方法
     * 工作在主线程
     *
     * @param objects
     * @return
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.d(TAG, "  doInBackground " + objects[0].toString());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "reslut";
    }
}

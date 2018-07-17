package com.core.net.retrofit.download;

import android.os.AsyncTask;

import com.core.net.retrofit.RestClient;
import com.core.net.retrofit.RestCreator;
import com.core.net.retrofit.callback.IError;
import com.core.net.retrofit.callback.IFailure;
import com.core.net.retrofit.callback.IRequest;
import com.core.net.retrofit.callback.ISuccess;

import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {

        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;

    }

    public void handleDownload() {
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService()
                .download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            final ResponseBody body = response.body();
                            final SaveFileTask saveFileTask = new SaveFileTask(REQUEST,SUCCESS);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR,EXTENSION,NAME,body);

                            //这里一定要注意判断，否则文件下载不全
                            if (saveFileTask.isCancelled()){
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (ERROR != null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}

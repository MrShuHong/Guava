package com.core.net.retrofit;


import android.content.Context;

import com.core.net.retrofit.callback.IError;
import com.core.net.retrofit.callback.IFailure;
import com.core.net.retrofit.callback.IRequest;
import com.core.net.retrofit.callback.ISuccess;
import com.core.net.retrofit.callback.RequestCallbacks;
import com.core.net.retrofit.download.DownloadHandler;
import com.core.ui.loader.LemonLoader;
import com.core.ui.loader.LemonStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;

public class RestClient {

    private final WeakHashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LemonStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    RestClient(String url,
               WeakHashMap<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               Context context,
               LemonStyle lemonStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = lemonStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    private void request(HttpMethod method) {

        RestService service = RestCreator.getRestService();

        Call<String> call = null;
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LemonLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(
                        MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part part = MultipartBody.Part.createFormData(
                        "file", FILE.getName(), requestBody);
                call = service.upload(URL, part);
                break;
            default:
                break;
        }
        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY != null){
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }else {
            request(HttpMethod.POST);
        }
    }

    public final void put(){
        if (BODY != null){
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }else {
            request(HttpMethod.PUT);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,PARAMS,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR).handleDownload();
    }
}

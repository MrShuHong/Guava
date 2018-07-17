package com.core.net.retrofit.rx;


import android.content.Context;

import com.core.net.retrofit.HttpMethod;
import com.core.net.retrofit.RestCreator;
import com.core.net.retrofit.RestService;
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

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

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


    private Observable<String> request(HttpMethod method) {

        RxRestService service = RestCreator.getRxRestService();

        Observable<String> observable = null;
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LemonLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(
                        MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part part = MultipartBody.Part.createFormData(
                        "file", FILE.getName(), requestBody);
                observable = service.upload(URL, part);
                break;
            default:
                break;
        }
        return observable;
    }

    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        if (BODY != null){
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }else {
            return request(HttpMethod.POST);
        }
    }

    public final Observable<String> put(){
        if (BODY != null){
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }else {
            return request(HttpMethod.PUT);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download(){
       return RestCreator.getRxRestService().download(URL, PARAMS);
    }
}

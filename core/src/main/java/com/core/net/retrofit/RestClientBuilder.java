package com.core.net.retrofit;

import android.content.Context;

import com.core.net.retrofit.callback.IError;
import com.core.net.retrofit.callback.IFailure;
import com.core.net.retrofit.callback.IRequest;
import com.core.net.retrofit.callback.ISuccess;
import com.core.ui.loader.LemonStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {

    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mRequestBody = null;
    private LemonStyle mLemonStyle = null;
    private File mFile = null;
    private Context mContext = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }


    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String exection) {
        this.mExtension = exection;
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        this.mIRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mISuccess = success;
        return this;
    }

    public final RestClientBuilder extension(IFailure failure) {
        this.mIFailure = failure;
        return this;
    }
    public final RestClientBuilder error(IError error) {
        this.mIError = error;
        return this;
    }
    public final RestClientBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RestClientBuilder loader(Context context, LemonStyle style) {
        this.mContext = context;
        this.mLemonStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLemonStyle = LemonStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mDownloadDir,
                mExtension, mName, mIRequest, mISuccess,
                mIFailure, mIError, mRequestBody,
                mFile, mContext, mLemonStyle);
    }
}

package com.core.net.retrofit.callback;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.core.app.ConfigKeys;
import com.core.app.Lemon;
import com.core.ui.loader.LemonLoader;
import com.core.ui.loader.LemonStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LemonStyle LOADER_STYLE;
    private static final Handler HANDLER = Lemon.getHandler();

    public RequestCallbacks(IRequest request, ISuccess success,
                            IFailure failure, IError error,
                            LemonStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(@NonNull Call<String> call,
                           @NonNull Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        onRequestFinish();
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        onRequestFinish();
    }

    private void onRequestFinish() {
        final long delayed = Lemon.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LemonLoader.stopLoading();
                }
            }, delayed);
        }
    }
}

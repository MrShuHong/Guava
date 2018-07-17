package com.core.net.retrofit;

import com.core.app.ConfigKeys;
import com.core.app.Lemon;
import com.core.net.retrofit.rx.RxRestService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 构建Retrofit
 */
public class RestCreator {



    /**
     * 构建OkHttp
     */
    private static final class OkHttpHolder {

        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS =
                                                    Lemon.getConfiguration(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null && INTERCEPTORS.size() > 0){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构建retrofit
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = Lemon.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * service 接口
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT
                .create(RestService.class);
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.RX_REST_SERVICE;
    }

    private static final class RxRestServiceHolder {
        private static final RxRestService RX_REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT
                .create(RxRestService.class);
    }
}

package com.core;

import android.os.Environment;
import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;

/**
 * Created by admin on 2018/5/16.
 */

public class TestOkHttp {
    @Test
    public void testGet(){
        //创建OkHttpClient实例对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建Request对象
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/get?id=111")
                .addHeader("key","value")
                .get()
                .build();
        //执行Request请求
        //异步请求
        /*okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                System.out.println(response.body().string());
                Log.d("TestOkHttp",response.body().string());
            }
        });*/
        //同步请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost(){
        //1、创建OkHttpClient对象实例
        OkHttpClient okHttpClient = new OkHttpClient();
        //2、创建Request对象
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,"{}");
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/post")
                .post(requestBody)
                .build();
        //3、执行Request请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInterceptor(){
        Interceptor loggingInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Logger logger = Logger.getGlobal();
                long t1 = System.nanoTime();
                logger.info(String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));

                Response response = chain.proceed(request);

                long t2 = System.nanoTime();
                logger.info(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

                return response;
            }
        };

        //1、创建OkHttpClient实例对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                //.addNetworkInterceptor() // 添加网络拦截器
                .build();
        //2、创建Request实例对象
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/get?id=111")
                .get()
                .build();
        //3、使用client执行request请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCache(){
        Cache cache = new Cache(new File(Environment.getDataDirectory(),"cache"),10*1024*1024);
        //1、创建OkHttpClient实例对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        //2、创建Request实例对象
        Request request = new Request.Builder()
                .url("")
                .get()
                .build();
        //3、使用client执行Request请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            //从缓存中获取响应
            Response cacheResponse = response.cacheResponse();
            //从网络中获取响应
            Response networkResponse = response.networkResponse();
            System.out.println(response.body().string());
            System.out.println(cacheResponse.body().string());
            System.out.println(networkResponse.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

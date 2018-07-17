package com.core.app;

import android.app.Activity;
import android.os.Handler;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

public final class Configurator {

    private static final HashMap<Object,Object> LEMON_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        LEMON_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        LEMON_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }
    public static  Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getLemonConfigs() {
        return LEMON_CONFIGS;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        LEMON_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

    public final Configurator withApiHost(String host){
        LEMON_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LEMON_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LEMON_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LEMON_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }


    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = LEMON_CONFIGS.get(key);
        if (value == null){
            throw new NullPointerException(key.toString() + "is NULL");
        }
        return (T) value;
    }

    private void checkConfiguration() {
        final boolean isReday = (boolean) LEMON_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReday){
            throw  new RuntimeException("Configuration is no ready,please call configure!");
        }
    }

}

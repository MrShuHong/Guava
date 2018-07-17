package com.core.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class Lemon {

    public static Configurator init(Context context){
        Configurator.getInstance()
                .getLemonConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context);
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

}

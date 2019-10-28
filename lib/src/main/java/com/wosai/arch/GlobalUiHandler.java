package com.wosai.arch;

import android.os.Handler;
import android.os.Looper;

public class GlobalUiHandler {
    private static Handler uiHandler;

    public static Handler getInstance(){
        if ( uiHandler==null ){
            uiHandler = new Handler(Looper.getMainLooper());
        }
        return uiHandler;
    }

}

package com.sxenon.arch;

import android.os.Handler;
import android.os.Looper;

public class GlobalUiHandler {
    private static class HandlerHolder {
        private static Handler uiHandler = new Handler(Looper.getMainLooper());
    }
    public static Handler getInstance(){
        return HandlerHolder.uiHandler;
    }

}

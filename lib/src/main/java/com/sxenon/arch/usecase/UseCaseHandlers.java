package com.sxenon.arch.usecase;

import com.sxenon.arch.usecase.scheduler.AsyncUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.CallbackOnUiUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.SyncUseCaseScheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseHandlers {
    private static final UseCaseHandler SIMPLE;
    private static final UseCaseHandler ASYNC;
    private static final UseCaseHandler UI;

    static {
        SIMPLE = new UseCaseHandler(new SyncUseCaseScheduler());
        //参考https://github.com/android/architecture-samples 中的todo-mvp-clean
        ASYNC = new UseCaseHandler(new AsyncUseCaseScheduler(new ThreadPoolExecutor(10, 100, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10))));
        UI = new UseCaseHandler(new CallbackOnUiUseCaseScheduler());
    }

    public static UseCaseHandler simple(){
        return SIMPLE;
    }

    public static UseCaseHandler async(){
        return ASYNC;
    }

    public static UseCaseHandler ui(){
        return UI;
    }
}

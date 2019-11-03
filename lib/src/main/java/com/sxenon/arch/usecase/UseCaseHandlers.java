package com.sxenon.arch.usecase;

import com.sxenon.arch.usecase.scheduler.AsyncUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.CallbackOnUiUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.SimpleUseCaseScheduler;

public class UseCaseHandlers {
    private static final UseCaseHandler SIMPLE;
    private static final UseCaseHandler ASYNC;
    private static final UseCaseHandler UI;

    static {
        SIMPLE = new UseCaseHandler(new SimpleUseCaseScheduler());
        ASYNC = new UseCaseHandler(new AsyncUseCaseScheduler());
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

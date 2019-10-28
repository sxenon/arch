package com.sxenon.arch.usecase.scheduler;

import com.sxenon.arch.usecase.UseCase;
import com.sxenon.arch.usecase.UseCaseScheduler;

public class SimpleUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        if ( useCaseCallback!=null ){
            useCaseCallback.onSuccess(response);
        }
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(UseCase.UseCaseCallback<V> useCaseCallback, Throwable throwable) {
        if ( useCaseCallback!=null ){
            useCaseCallback.onError(throwable);
        }
    }
}

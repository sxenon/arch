package com.sxenon.arch.usecase.scheduler;

import com.sxenon.arch.GlobalUiHandler;
import com.sxenon.arch.usecase.UseCase;

public class CallbackOnUiUseCaseScheduler extends BaseUseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        GlobalUiHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
               CallbackOnUiUseCaseScheduler.super.notifyResponse(response, useCaseCallback);
            }
        });
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback, final Throwable throwable) {
        GlobalUiHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                CallbackOnUiUseCaseScheduler.super.onError(useCaseCallback, throwable);
            }
        });
    }
}

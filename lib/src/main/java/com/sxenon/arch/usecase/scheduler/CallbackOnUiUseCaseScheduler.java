package com.sxenon.arch.usecase.scheduler;

import com.sxenon.arch.GlobalUiHandler;
import com.sxenon.arch.usecase.UseCase;
import com.sxenon.arch.usecase.UseCaseScheduler;

public class CallbackOnUiUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        GlobalUiHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback, final Throwable throwable) {
        GlobalUiHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError(throwable);
            }
        });
    }
}

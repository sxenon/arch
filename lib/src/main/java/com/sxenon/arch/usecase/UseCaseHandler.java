/*
 * Copyright (c) 2018  sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.arch.usecase;

import com.sxenon.arch.usecase.scheduler.AsyncUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.CallbackOnUiUseCaseScheduler;
import com.sxenon.arch.usecase.scheduler.SimpleUseCaseScheduler;

/**
 * Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 */
public class UseCaseHandler {
    private static final UseCaseHandler SIMPLE;
    private static final UseCaseHandler ASYNC;
    private static final UseCaseHandler UI;

    static {
        SIMPLE = new UseCaseHandler(new SimpleUseCaseScheduler());
        ASYNC = new UseCaseHandler(new AsyncUseCaseScheduler());
        UI = new UseCaseHandler(new CallbackOnUiUseCaseScheduler());
    }

    private final UseCaseScheduler mUseCaseScheduler;

    UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
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

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper<>(callback, this));

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    private  <V extends UseCase.ResponseValue> void notifyResponse(final V response,
            final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    private <V extends UseCase.ResponseValue> void notifyError(
            final UseCase.UseCaseCallback<V> useCaseCallback, Throwable throwable) {
        mUseCaseScheduler.onError(useCaseCallback, throwable);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError(Throwable t) {
            mUseCaseHandler.notifyError(mCallback, t);
        }
    }
}

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

package com.sxenon.arch.viewmodule.verificationcode;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.sxenon.arch.controller.IController;
import com.sxenon.arch.response.IResponseHandler;
import com.sxenon.arch.viewmodule.IViewModule;

/**
 * Demo for VerificationCodeViewHolder
 * Created by Sui on 2017/8/14.
 * See Demo
 */

public abstract class BaseVerificationCodeViewModule implements IViewModule, IResponseHandler {
    private static final String TAG = "Demo";
    private final Button mCodeBtn;
    private final TextView mCountDownTv;
    private final IController mController;

    public BaseVerificationCodeViewModule(IController controller, final Button codeBtn, final TextView countDownTv) {
        mController = controller;
        mCodeBtn = codeBtn;
        mCountDownTv = countDownTv;
    }

    /*
        点了发送验证码的按钮，成功发送，才会启动冷却。所以是被动的
     */

    /**
     * 先创建一个BaseVerificationCodeUseCase 然后再调用excuse方法。
     * @param secondsInFuture
     */
    public abstract void startCountDown(int secondsInFuture);

    public IController getController() {
        return mController;
    }

    public Button getCodeBtn() {
        return mCodeBtn;
    }

    public TextView getCountDownTv() {
        return mCountDownTv;
    }

    @Override
    public Context getContext() {
        return mCodeBtn.getContext();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

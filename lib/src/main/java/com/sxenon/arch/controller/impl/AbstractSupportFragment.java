/*
 * Copyright (c) 2017 sxenon
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

package com.sxenon.arch.controller.impl;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sxenon.arch.controller.handler.ActivityResultHandler;
import com.sxenon.arch.controller.IFragment;
import com.sxenon.arch.controller.handler.RequestSpecialPermissionResultHandler;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class AbstractSupportFragment<P extends AbstractControllerVisitorAsPresenter> extends Fragment implements IFragment<P> {
    private AppCompatActivity mHost;
    private P mPresenter;
    private ActivityResultHandler activityResultHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHost = (AppCompatActivity) activity;
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = bindPresenter();
    }

    @Override
    public P getPresenter() {
        if(mPresenter == null){
            mPresenter = bindPresenter();
        }
        return mPresenter;
    }

    @Override
    public AbstractCompactActivity getActivityCompact() {
        return (AbstractCompactActivity) mHost;
    }

    @Override
    public void requestPermissionsWithAction(@NonNull String[] permissions, int requestCode, Runnable runnable) {
        getPresenter().checkIfHasSpecialPermissions(permissions);
        getPresenter().setPermissionEvent(requestCode, runnable);
        requestPermissions(permissions, requestCode);
    }

    @Override
    public final ControllerType getControllerType() {
        return ControllerType.SUPPORT_FRAGMENT;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(permissions, grantResults);
    }

    @Override
    public void startActivityForResultWithHandler(Intent intent, int requestCode, ActivityResultHandler handler) {
        activityResultHandler = handler;
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void startActivityForResultWithHandler(Intent intent, int requestCode, @Nullable Bundle options, ActivityResultHandler handler) {
        activityResultHandler = handler;
        startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void requestSpecialPermission(int requestCode, RequestSpecialPermissionResultHandler handler, String specialPermission) {
        getPresenter().requestSpecialPermission(requestCode, handler,specialPermission);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getPresenter().onSpecialPermissionResult()){
            return;
        }
        activityResultHandler.onResult(requestCode, resultCode, data);
    }

    @Override
    public ViewModelProvider getViewModelProvider() {
        return ViewModelProviders.of(this);
    }

    @Override
    public ViewModelProvider getViewModelProvider(ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(this,factory);
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public void finish() {
        getActivityCompact().finish();
    }
}

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

package com.sxenon.arch.controller;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxenon.arch.controller.handler.ActivityResultHandler;
import com.sxenon.arch.controller.handler.RequestSpecialPermissionResultHandler;
import com.sxenon.arch.controller.impl.AbstractControllerVisitorAsPresenter;
import com.sxenon.arch.mvp.IView;
import com.sxenon.arch.viewmodule.ILoadingView;
import com.sxenon.arch.viewmodule.IViewModule;

/**
 * Treat Activity & Fragment as controller
 * Architecture: IController-->{@link IViewModule} with Business logic/ViewGroup-->View/Adapter
 * Created by Sui on 2016/11/20.
 */

public interface IController<P extends AbstractControllerVisitorAsPresenter> extends IView<P>,LifecycleOwner {

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResultWithHandler(Intent intent, int requestCode, ActivityResultHandler handler);

    void startActivityForResultWithHandler(Intent intent, int requestCode, @Nullable Bundle options,ActivityResultHandler handler);

    Activity getActivityCompact();

    void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting);

    /**
     * There are a couple of permissions that don't behave like normal and dangerous permissions.
     * SYSTEM_ALERT_WINDOW and WRITE_SETTINGS are particularly sensitive, so most apps should not use them.
     * If an app needs one of these permissions, it must declare the permission in the manifest, and send an intent requesting the user's authorization.
     * The system responds to the intent by showing a detailed management screen to the user.
     */
    void requestSpecialPermission(int requestCode, RequestSpecialPermissionResultHandler handler, String specialPermission);

    /**
     * @return Return false if the controller is instance of FragmentActivity and its supportFragment request the permission,otherwise true.
     */
    boolean requestPermissionsBySelf(int requestCode);

    boolean shouldShowRequestPermissionRationale(String permission);

    P bindPresenter();

    @Override
    P getPresenter();

    ILoadingView getLoadingView();

    ControllerType getControllerType();

    enum ControllerType {
        COMPACT_ACTIVITY,
        SUPPORT_FRAGMENT,
    }

    ViewModelProvider getViewModelProvider();

    ViewModelProvider getViewModelProvider(ViewModelProvider.Factory factory);

    Bundle getBundle();

    void finish();

    boolean isReallyVisibleToUser();
}

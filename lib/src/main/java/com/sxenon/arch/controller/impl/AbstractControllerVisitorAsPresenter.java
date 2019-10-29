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

package com.sxenon.arch.controller.impl;

import android.Manifest;
import android.support.annotation.NonNull;

import com.sxenon.arch.Event;
import com.sxenon.arch.controller.IController;
import com.sxenon.arch.controller.IControllerVisitor;
import com.sxenon.arch.controller.handler.RequestSpecialPermissionResultHandler;
import com.sxenon.arch.mvp.BasePresenter;
import com.sxenon.arch.permission.PermissionCompat;
import com.sxenon.arch.permission.PermissionHelper;

import java.util.Arrays;

/**
 * RouterVisitor
 * Created by Sui on 2016/11/28.
 */

public abstract class AbstractControllerVisitorAsPresenter<C extends IController> extends BasePresenter<C> implements IControllerVisitor<C> {

    private final PermissionHelper permissionHelper;
    private RequestSpecialPermissionResultHandler requestSpecialPermissionResultHandler;
    private String specialPermission;

    public static final String TAG = "AbstractControllerVisitorAsPresenter";

    public AbstractControllerVisitorAsPresenter(C controller) {
        super(controller);
        permissionHelper = new PermissionHelper(controller, this);
    }

    //Permission start
    public void setPermissionEvent(int what, Runnable runnable) {
        permissionHelper.setPermissionEvent(what, runnable);
    }

    public Event getPermissionEvent(){
        return permissionHelper.getPermissionEvent();
    }

    public boolean onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (getPermissionEvent()==null){
            return false;
        }
        permissionHelper.onRequestPermissionsResult(permissions, grantResults);
        return true;
    }

    void requestSpecialPermission(int requestCode, RequestSpecialPermissionResultHandler handler, String specialPermission){
        if (PermissionCompat.specialPermissionGranted(getController(),specialPermission)){
            handler.onResult(true);
        }else {
            requestSpecialPermissionResultHandler = handler;
            this.specialPermission = specialPermission;
            PermissionCompat.requestSpecialPermission(getController(),requestCode,specialPermission);
        }
    }

    boolean onSpecialPermissionResult(){
        if (specialPermission !=null){
            requestSpecialPermissionResultHandler.onResult(PermissionCompat.specialPermissionGranted(getController(), specialPermission));
            requestSpecialPermissionResultHandler = null;
            specialPermission = null;
            return true;
        }
        return false;
    }

    void checkIfHasSpecialPermissions(@NonNull String[] permissions) {
        if (Arrays.binarySearch(permissions, Manifest.permission.SYSTEM_ALERT_WINDOW) >= 0) {
            throw new IllegalArgumentException("Please Call requestSpecialPermission for SYSTEM_ALERT_WINDOW!");
        }
        if (Arrays.binarySearch(permissions, Manifest.permission.WRITE_SETTINGS) >= 0) {
            throw new IllegalArgumentException("Please Call requestSpecialPermission(int requestCode, Runnable runnable) for WRITE_SETTINGS!");
        }
    }

    @Override
    public final void onPermissionGranted(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void onPermissionDeclined(int requestCode, @NonNull String[] permissions) {

    }

    @Override
    public void onPermissionPermanentlyDeclined(int requestCode, @NonNull String[] permissions) {

    }

    //Permission end

    @NonNull
    public IController getController() {
        return getView();
    }

}

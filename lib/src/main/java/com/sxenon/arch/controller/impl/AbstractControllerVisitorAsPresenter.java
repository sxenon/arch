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

import com.sxenon.arch.controller.IController;
import com.sxenon.arch.controller.IControllerVisitor;
import com.sxenon.arch.controller.handler.RequestOverlayPermissionResultHandler;
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
    private RequestOverlayPermissionResultHandler requestOverlayPermissionResultHandler;

    public static final String TAG = "AbstractControllerVisitorAsPresenter";

    public AbstractControllerVisitorAsPresenter(C controller) {
        super(controller);
        permissionHelper = new PermissionHelper(controller, this);
    }

    //Permission start
    public void setPermissionEvent(int what, Runnable runnable, boolean forceAccepting) {
        permissionHelper.setPermissionEvent(what, runnable, forceAccepting);
    }

    /**
     * Please ignore the return value unless the router type is COMPACT_ACTIVITY.
     *
     * @return Handle the result by self or deliver to its fragment,if the router type is COMPACT_ACTIVITY.
     */
    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (getController().requestPermissionsBySelf(requestCode)) {
                if (permissionHelper.getPermissionEvent() == null) {
                    throw new IllegalStateException("Please call requestPermissionsCompact in controller(view) or requestPermissions in controllerVisitor(presenter)");
                }
                permissionHelper.onRequestPermissionsResult(permissions, grantResults);
                return true;
            }
        } catch (Exception ignore){

        }
        return false;
    }

    public void requestOverlayPermission(int requestCode, RequestOverlayPermissionResultHandler handler){
        if (PermissionCompat.isOverlayGranted(getController())){
            handler.onResult(true);
        }else {
            requestOverlayPermissionResultHandler = handler;
            PermissionCompat.requestOverlayPermission(getController(),requestCode);
        }
    }

    public boolean onOverlayPermissionResult(){
        if (requestOverlayPermissionResultHandler !=null){
            requestOverlayPermissionResultHandler.onResult(PermissionCompat.isOverlayGranted(getController()));
            requestOverlayPermissionResultHandler = null;
            return true;
        }
        return false;
    }

    @Override
    public final void requestPermissions(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting) {
        if (Arrays.binarySearch(permissions, Manifest.permission.SYSTEM_ALERT_WINDOW) >= 0) {
            throw new IllegalArgumentException("Please Call requestOverlayPermission(int requestCode, Runnable runnable) for SYSTEM_ALERT_WINDOW!");
        }
        permissionHelper.requestPermissions(permissions, requestCode, runnable, forceAccepting);
    }

    /**
     * @return if true,don`t forget to call {@link #requestPermissionsAfterExplanation} first!
     */
    @Override
    public boolean shouldExplainPermissionBeforeRequest(int requestCode, String[] permissions) {
        return false;
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

    @Override
    public final void requestPermissionsAfterExplanation(@NonNull String[] permissions) {
        permissionHelper.requestPermissionsAfterExplanation(permissions);
    }
    //Permission end

    @NonNull
    public IController getController() {
        return getView();
    }

}

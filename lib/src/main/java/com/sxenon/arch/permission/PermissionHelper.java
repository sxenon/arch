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

package com.sxenon.arch.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sxenon.arch.Event;
import com.sxenon.arch.controller.IController;

import java.util.List;

/**
 * Inspired by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionHelper {
    private Event permissionEvent;
    @NonNull
    private final OnPermissionCallback permissionCallback;
    @NonNull
    private final IController controller;

    public PermissionHelper(@NonNull IController controller, @NonNull OnPermissionCallback permissionCallback) {
        this.controller = controller;
        this.permissionCallback = permissionCallback;
    }

    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (verifyPermissions(grantResults)) {
            permissionCallback.onPermissionGranted((Runnable) permissionEvent.obj);
        } else {
            String[] declinedPermissions = PermissionCompat.getDeclinedPermissionArray(controller, permissions);
            List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(controller, declinedPermissions);
            if (!permissionPermanentlyDeniedList.isEmpty()) {
                permissionCallback.onPermissionPermanentlyDeclined(permissionEvent.what, permissionPermanentlyDeniedList.toArray(new String[permissionPermanentlyDeniedList.size()]));
            } else {
                permissionCallback.onPermissionDeclined(permissionEvent.what, declinedPermissions);
            }
        }
        permissionEvent = null;
    }

    public Event getPermissionEvent() {
        return permissionEvent;
    }

    public void setPermissionEvent(int what, Runnable runnable) {
        permissionEvent = new Event();
        permissionEvent.what = what;
        permissionEvent.obj = runnable;
        permissionEvent.data = new Bundle();
    }

    public void requestPermissions(@NonNull String[] permissions, int what, Runnable runnable, boolean forceAccepting) {
        List<String> permissionsNeeded = PermissionCompat.getDeclinedPermissionList(controller, permissions);
        if (permissionsNeeded.isEmpty()) {
            runnable.run();
            return;
        }

        String[] permissionsNeedArray = permissionsNeeded.toArray(new String[permissionsNeeded.size()]);
        List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(controller, permissionsNeedArray);
        if (!permissionPermanentlyDeniedList.isEmpty()) {
            permissionCallback.onPermissionPermanentlyDeclined(what, permissions);
            return;
        }

        setPermissionEvent(what, runnable);
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestPermissionsAfterExplanation(@NonNull String[] permissions) {
        controller.requestPermissionsWithHandler(permissions, permissionEvent.what, (Runnable) permissionEvent.obj);
    }

    /**
     * internal usage.
     */
    private boolean verifyPermissions(@NonNull int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}

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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.sxenon.arch.controller.IController;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionCompat {
    /**
     * be aware as it might return null (do check if the returned result is not null!)
     * <p/>
     * can be used outside of activity.
     */
    @Nullable
    public static String getFirstDeclinedPermission(@NonNull IController controller, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (isPermissionDeclined(controller, permission)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * @return list of permissions that the user declined or not yet granted.
     */
    public static String[] getDeclinedPermissionArray(@NonNull IController controller, @NonNull String[] permissions) {
        List<String> declinedPermissions = getDeclinedPermissionList(controller, permissions);
        return declinedPermissions.toArray(new String[declinedPermissions.size()]);
    }

    public static List<String> getDeclinedPermissionList(@NonNull IController controller, @NonNull String[] permissions) {
        List<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionDeclined(controller, permission) && isPermissionExisted(controller, permission)) {
                permissionsNeeded.add(permission);
            }
        }
        return permissionsNeeded;
    }

    /**
     * return true if permission is granted, false otherwise.
     * <p/>
     * can be used outside of controller.
     */
    public static boolean isPermissionGranted(@NonNull IController controller, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(controller.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * return true if permission is declined, false otherwise.
     * <p/>
     * can be used outside of controller.
     */
    public static boolean isPermissionDeclined(@NonNull IController controller, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(controller.getContext(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @return true if explanation needed.
     */
    public static boolean isExplanationNeeded(@NonNull IController controller, @NonNull String permissionName) {
        return controller.shouldShowRequestPermissionRationale(permissionName);
    }

    /**
     * @return true if the permission is patently denied by the user and only can be granted via settings Screen
     */
    public static boolean isPermissionPermanentlyDenied(@NonNull IController controller, @NonNull String permission) {
        return isPermissionDeclined(controller, permission) && !isExplanationNeeded(controller, permission);
    }

    public static List<String> getPermissionPermanentlyDeniedList(@NonNull IController controller, @NonNull String[] permissions) {
        List<String> permissionPermanentlyDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionPermanentlyDenied(controller, permission)) {
                permissionPermanentlyDeniedList.add(permission);
            }
        }
        return permissionPermanentlyDeniedList;
    }

    /**
     * @return true if permission exists in the manifest, false otherwise.
     */
    public static boolean isPermissionExisted(@NonNull IController controller, @NonNull String permission) {
        Context context = controller.getContext();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return true if {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW} is granted
     */
    public static boolean isOverlayGranted(@NonNull IController controller) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(controller.getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestOverlayPermission(@NonNull IController controller, int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + controller.getContext().getPackageName()));
        controller.startActivityForResult(intent, requestCode);
    }
}

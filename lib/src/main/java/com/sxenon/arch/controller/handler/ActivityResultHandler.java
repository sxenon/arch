package com.sxenon.arch.controller.handler;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface ActivityResultHandler {
    void onResult(int requestCode, int resultCode, @Nullable Intent data);
}

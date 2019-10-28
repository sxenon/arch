package com.sxenon.arch.controller;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface ActivityResultHandler {
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}

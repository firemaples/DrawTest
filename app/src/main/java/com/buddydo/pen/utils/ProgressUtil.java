package com.buddydo.pen.utils;

import android.app.Activity;
import android.graphics.Color;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by louis1chen on 27/02/2018.
 */

public class ProgressUtil {
    private final Activity activity;

    private ACProgressFlower dialog;

    public ProgressUtil(Activity activity) {
        this.activity = activity;
    }

    public void show() {
        dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Progressing...")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

package com.buddydo.bdd.samsungtools.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by louis1chen on 26/02/2018.
 */

public class Utils {
    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }
}

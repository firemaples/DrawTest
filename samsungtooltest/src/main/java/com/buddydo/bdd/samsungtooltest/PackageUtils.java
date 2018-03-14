package com.buddydo.bdd.samsungtooltest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class PackageUtils {

    public static void showPlayStore(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + packageName)));
        } catch (ActivityNotFoundException exception) {
            startPlayStoreByBrowser(context, packageName);
        }
    }

    public static void startPlayStoreByBrowser(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("http://play.google.com/store/apps/details?id=" + packageName)));
        } catch (ActivityNotFoundException exception) {
            /** ignore */
        }
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // should not happen
            throw new RuntimeException(e);
        }
    }

    public static boolean isAppDebuggable(Context context) {
        return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    public static boolean isDevelopmentMode(Context context) {
        if ("10.0.0".equals(getVersionName(context)) && isAppDebuggable(context)) {
            return true;
        }

        return false;
    }

}

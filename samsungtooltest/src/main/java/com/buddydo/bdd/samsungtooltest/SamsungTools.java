package com.buddydo.bdd.samsungtooltest;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by louis1chen on 26/02/2018.
 */

public class SamsungTools {
    private static final Logger logger = LoggerFactory.getLogger(SamsungTools.class);

    private static final int REQUEST_DRAW = 1;

    private static Intent getDrawToolIntent() {
        Intent intent = new Intent();
//        intent.setAction("com.buddydo.pen.DRAW");
        intent.setComponent(new ComponentName("com.buddydo.pen", "com.buddydo.pen.DrawActivity"));
        intent.setPackage("com.buddydo.pen");
        return intent;
    }

    public static boolean isDrawToolInstalled(Context context) {
        return checkInstalled(context, getDrawToolIntent());
    }

    private static boolean checkInstalled(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    public static void startDraw(Fragment fragment, Uri image) {
        Intent intent = getDrawToolIntent();

        if (checkInstalled(fragment.getActivity(), intent)) {
            intent.setData(image);
            fragment.startActivityForResult(intent, REQUEST_DRAW);
        }
    }

    public static void startDraw(Activity activity, Uri image) {
        Intent intent = getDrawToolIntent();

        if (checkInstalled(activity, intent)) {
            intent.setData(image);
            activity.startActivityForResult(intent, REQUEST_DRAW);
        }
    }

    public static Uri handleDrawResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_DRAW && data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    logger.info("Draw result got: " + fileUri.getPath());
                    return fileUri;
                }
            }
        }
        return null;
    }
}

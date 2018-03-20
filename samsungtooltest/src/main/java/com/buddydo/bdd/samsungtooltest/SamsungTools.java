package com.buddydo.bdd.samsungtooltest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by louis1chen on 26/02/2018.
 */

public class SamsungTools {
    private static final Logger logger = LoggerFactory.getLogger(SamsungTools.class);

    private static final int REQUEST_DRAW = 1;

    private static final String SAMSUNG = "SAMSUNG";
    private static final String NOTE = "GT-N";
    private static final String SPEN_FEATURE = "com.sec.feature.spen_usp";
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    private static final List<String> mSPenDevices = Arrays.asList(
            "SM-N950", //Galaxy Note 8
            "SM-N920", //Galaxy Note 5
            "SM-N910" //Galaxy Note 4
    );

    private static final String toolsAppPackageName = "com.buddydo.pen";
    private static final String toolsAppDrawActivity = ".DrawActivity";

    public static boolean isDeviceSupported(Context context) {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS) {
            return false;
        }

//        FeatureInfo[] infos = context.getPackageManager().getSystemAvailableFeatures();
//        for (FeatureInfo info : infos) {
//            if (SPEN_FEATURE.equalsIgnoreCase(info.name)) {
//                return true;
//            }
//        }
//
//        if (Build.MODEL.toUpperCase(Locale.ENGLISH).startsWith(NOTE)) {
//            return true;
//        }

        if (SAMSUNG.equalsIgnoreCase(Build.MANUFACTURER)) {
            if (Build.MODEL != null) {
                for (String model : mSPenDevices) {
                    if (Build.MODEL.toUpperCase().contains(model.toUpperCase())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static Intent getDrawToolIntent() {
        Intent intent = new Intent();
//        intent.setAction("com.buddydo.pen.DRAW");
        intent.setComponent(new ComponentName(toolsAppPackageName, toolsAppPackageName + toolsAppDrawActivity));
        intent.setPackage(toolsAppPackageName);
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
        } else {
            showNotInstalledDialog(fragment.getActivity());
        }
    }

    public static void startDraw(Activity activity, Uri image) {
        Intent intent = getDrawToolIntent();

        if (checkInstalled(activity, intent)) {
            intent.setData(image);
            activity.startActivityForResult(intent, REQUEST_DRAW);
        } else {
            showNotInstalledDialog(activity);
        }
    }

    private static void showNotInstalledDialog(final Activity activity) {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("您尚未安裝 [DoPen Tool], 是否要下載安裝?");
        ab.setPositiveButton("下載", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PackageUtils.showPlayStore(activity, toolsAppPackageName);
            }
        });
        ab.setNegativeButton("取消", null);
        ab.show();
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

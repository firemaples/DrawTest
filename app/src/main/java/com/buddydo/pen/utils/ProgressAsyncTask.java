package com.buddydo.pen.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by louis1chen on 27/02/2018.
 */

public abstract class ProgressAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static final String TAG = ProgressAsyncTask.class.getSimpleName();

    private final WeakReference<Activity> weakActivity;
    private ProgressUtil progressUtil;

    public ProgressAsyncTask(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Activity activity = getActivity();
        if (activity != null) {
            progressUtil = new ProgressUtil(activity);
            progressUtil.show();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        Log.i(TAG, "before dismiss");
        if (progressUtil != null) {
            Log.i(TAG, "before dismiss 1");
            progressUtil.dismiss();
        }
    }

    protected Activity getActivity() {
        Activity activity = weakActivity.get();
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            return activity;
        }
        return null;
    }
}

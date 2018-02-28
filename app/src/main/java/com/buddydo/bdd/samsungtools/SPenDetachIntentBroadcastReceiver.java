package com.buddydo.bdd.samsungtools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * https://stackoverflow.com/a/36223432/2906153
 *
 * Created by louis1chen on 01/03/2018.
 */

public abstract class SPenDetachIntentBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_SPEN_INSERT = "com.samsung.pen.INSERT";

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_SPEN_INSERT));
    }

    public void unregister(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        boolean penInsert = intent.getBooleanExtra("penInsert", true);
        onReceive(context, intent, penInsert);
    }

    abstract void onReceive(Context context, Intent intent, boolean sPenInserted);
}

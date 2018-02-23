package com.buddydo.bdd.samsungtooltest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_DRAW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
    }

    private void setViews() {
        findViewById(R.id.bt_startDraw).setOnClickListener(onClickListener);
        findViewById(R.id.bt_checkDraw).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.bt_startDraw) {
                startDraw();
            } else if (id == R.id.bt_checkDraw) {
                boolean result = checkInstalled();
                Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Intent startDrawIntent() {
        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.buddydo.bdd.samsungtools", "com.buddydo.bdd.samsungtools.DrawActivity"));
        intent.setAction("com.buddydo.bdd.samsungtools.DRAW");
//        intent.setPackage("com.buddydo.bdd.samsungtools");
        return intent;
    }

    private void startDraw() {
        startActivityForResult(startDrawIntent(), REQUEST_DRAW);
    }

    private boolean checkInstalled() {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startDrawIntent(),
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }
}

package com.buddydo.bdd.samsungtooltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private final int REQUEST_DRAW = 1;

    private ImageView iv_resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
    }

    private void setViews() {
        findViewById(R.id.bt_startDraw).setOnClickListener(onClickListener);
        findViewById(R.id.bt_uninstallTools).setOnClickListener(onClickListener);

        iv_resultImage = findViewById(R.id.iv_resultImage);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.bt_startDraw) {
                startDraw();
            } else if (id == R.id.bt_uninstallTools) {
                uninstall();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_DRAW && data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    Log.i(TAG, "Draw result got: " + fileUri.getPath());
                    Glide.with(this)
                            .load(fileUri)
                            .into(iv_resultImage);
                }
            }
        }
    }

    private void startDraw() {
        Intent intent = new Intent();
//        intent.setAction("com.buddydo.bdd.samsungtools.DRAW");
        intent.setComponent(new ComponentName("com.buddydo.bdd.samsungtools", "com.buddydo.bdd.samsungtools.DrawActivity"));
        intent.setPackage("com.buddydo.bdd.samsungtools");
        intent.setData(Uri.parse("https://www.buddydo.us/t3/MTY0NTY2ae88_T.png"));

        if (checkInstalled(intent)) {
            startActivityForResult(intent, REQUEST_DRAW);
        } else {
            Toast.makeText(this, "SamsungTools not installed or version is too old", Toast.LENGTH_SHORT).show();
        }
    }

    private Intent prepareTempFileUri() {
        File file = new File(getFilesDir(), "images/" + System.currentTimeMillis() + ".png");
        Uri uriToImage = FileProvider.getUriForFile(this, "com.buddydo.bdd.samsungtooltest.fileprovider", file);
        Intent intent = ShareCompat.IntentBuilder.from(this)
                .setStream(uriToImage)
                .getIntent();
        intent.setData(uriToImage);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }

    private boolean checkInstalled(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    private void uninstall() {
        Uri packageUri = Uri.parse("package:com.buddydo.bdd.samsungtools");
        Intent uninstallIntent =
                new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        startActivity(uninstallIntent);
    }
}

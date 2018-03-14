package com.buddydo.bdd.samsungtooltest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {
    private final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

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

        iv_resultImage = (ImageView) findViewById(R.id.iv_resultImage);
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

            Uri uri = SamsungTools.handleDrawResult(requestCode, resultCode, data);
            if (uri != null) {
                logger.info("Draw result got: " + uri.getPath());
                Glide.with(this)
                        .load(uri)
                        .into(iv_resultImage);
            }
        }
    }

    private void startDraw() {
//        Intent intent = new Intent();
////        intent.setAction("com.buddydo.bdd.samsungtools.DRAW");
//        intent.setComponent(new ComponentName("com.buddydo.bdd.samsungtools", "com.buddydo.bdd.samsungtools.DrawActivity"));
//        intent.setPackage("com.buddydo.bdd.samsungtools");
//        intent.setData(Uri.parse("https://www.buddydo.us/t3/MTY0NTY2ae88_T.png"));

        Uri uri = Uri.parse("https://www.buddydo.us/t3/MTY0NTY2ae88_T.png");

        if (SamsungTools.isDrawToolInstalled(this)) {
            SamsungTools.startDraw(this, uri);
        } else {
            Toast.makeText(this, "SamsungTools not installed or version is too old", Toast.LENGTH_SHORT).show();
        }
    }

    private void uninstall() {
        Uri packageUri = Uri.parse("package:com.buddydo.pen");
        Intent uninstallIntent =
                new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        startActivity(uninstallIntent);
    }
}

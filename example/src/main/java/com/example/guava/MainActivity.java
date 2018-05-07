package com.example.guava;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.statusbar.StatusBarMainActivity;
import com.qrcode.zxing.app.CaptureActivity;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;
    private TextView mTxtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtContent = findViewById(R.id.txt_content);
        findViewById(R.id.btn_qrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    //校验是否已具有模糊定位权限
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                    } else {
                        //具有权限

                        Intent inten = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivityForResult(inten, 1000);
                    }
                } else {
                    //系统不高于6.0直接执行
                    Intent inten = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(inten, 1000);
                }


            }
        });

        findViewById(R.id.btn_statusbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inten = new Intent(MainActivity.this, StatusBarMainActivity.class);
                startActivityForResult(inten, 1000);
            }
        });

        RequestOptions requestOptions = new RequestOptions()
                .transform(new BitmapTransformation() {
                    @Override
                    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {


                        if (toTransform != null){

                        }

                        return toTransform;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                });
        Glide.with(this)
                .load("https://devapinew2.jqian.com/Data/images/exclusive/cash.png")
                .apply(requestOptions)
                .into(new ImageView(this));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意权限,做跳转逻辑
                Intent inten = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(inten, 1000);
            } else {
                // 权限拒绝，提示用户开启权限
                //denyPermission();
                Toast.makeText(this, "没有权限，不能使用拍照功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && data != null) {
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            mTxtContent.setText(result);
        }
    }
}

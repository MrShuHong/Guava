package com.example.guava;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qrcode.zxing.app.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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


        findViewById(R.id.btn_material_design).setOnClickListener(this);
        findViewById(R.id.btn_hencoder).setOnClickListener(this);
        findViewById(R.id.btn_recycler).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_material_design:
                ARouter.getInstance().build("/material_design/main").navigation();
                break;

            case R.id.btn_hencoder:
                ARouter.getInstance().build("/hencoder/main").navigation();
                break;

            case R.id.btn_recycler:
                ARouter.getInstance().build("/widget/main").navigation();
                break;
            default:
                break;
        }
    }
}

package com.e21cn.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.e21cn.R;
import com.e21cn.baseapplication.BaseActivity;
import com.e21cn.module.MainContract;
import com.e21cn.module.MainPresenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liangminqiang
 * @Description : 主界面Activity,处理View部分
 * @class : MainActivity
 * @time Create at 6/4/2018 4:24 PM
 */


public class MainActivity extends BaseActivity implements MainContract.View{

    @BindView(R.id.imageView)ImageView imageView;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.button) Button button;
    @OnClick(R.id.button)
    public void takePic(){
        takePhoto();
    }

    private MainPresenter mPresenter;
    File mTmpFile;
    Uri imageUri;

    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;


    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mPresenter = new MainPresenter(this);
    }



    @Override
    public void updateUI(String s) {
        textView.setText(s);
    }

    private void takePhoto(){
try {
    if (!hasPermission()) {
        return;
    }

    Intent intent = new Intent();
    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img";
    if (new File(path).exists()) {
        try {
            new File(path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    mTmpFile = new File(path, filename + ".jpg");
    mTmpFile.getParentFile().mkdirs();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        String authority = "com.e21cn.provider";//getPackageName() + ".provider";
        imageUri = FileProvider.getUriForFile(this, authority, mTmpFile);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    } else {
        imageUri = Uri.fromFile(mTmpFile);
    }
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

    startActivityForResult(intent, CAMERA_REQUEST_CODE);
}catch (Exception e){
    e.printStackTrace();
}
    }


    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                takePhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap photo = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath());
                mPresenter.getAccessToken(photo);
                imageView.setImageBitmap(photo);
        }
    }

}

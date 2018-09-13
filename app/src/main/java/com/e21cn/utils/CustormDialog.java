package com.e21cn.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.e21cn.R;


/**
 * Created by liangminqiang on 2018/8/1.
 */

public class CustormDialog {

    private boolean hasshowdialog=false;
    Activity activity;
    View.OnClickListener onClickListener;
     Dialog dialog;
    public CustormDialog(Activity activity, View.OnClickListener onClickListener){
        this.activity=activity;
        this.onClickListener=onClickListener;
    }
    public void dismiss(){
        dialog.cancel();
        dialog.dismiss();
    }
    public  void showPopupWindow_Version(final String updateurl) {//版本提示
        if(activity==null)
            return;

        if(hasshowdialog)
            return;

        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.dialog_update, null);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
         dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                hasshowdialog=false;
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hasshowdialog=false;
            }
        });

        // 设置按钮的点击事件
        TextView title=(TextView)contentView.findViewById(R.id.title);
        title.setText("有新的版本请更新!");
        Button cancle=(Button)contentView.findViewById(R.id.cancle);
        Button sure=(Button)contentView.findViewById(R.id.sure);

        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hasshowdialog=false;
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(onClickListener);



        Window window = dialog.getWindow();

        //  window.setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.8f;    // 设置透明度为0.5
        window.setAttributes(lp);
        WindowManager.LayoutParams params = window.getAttributes();
        //params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
        window.setAttributes(params);

        dialog.show();
        hasshowdialog=true;
        dialog.getWindow().setContentView(contentView);
    }
    public  void showPopupWindow(final String titllestr,final String canclestr,final String surestr) {//弹出提示框
        if(activity==null)
            return;

        if(hasshowdialog)
            return;

        View contentView = LayoutInflater.from(activity).inflate(
                R.layout.dialog_update, null);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                hasshowdialog=false;
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hasshowdialog=false;
            }
        });

        // 设置按钮的点击事件
        TextView title=(TextView)contentView.findViewById(R.id.title);
        title.setText(titllestr);
        Button cancle=(Button)contentView.findViewById(R.id.cancle);
        Button sure=(Button)contentView.findViewById(R.id.sure);
        cancle.setText(canclestr);
        sure.setText(surestr);

        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hasshowdialog=false;
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(onClickListener);



        Window window = dialog.getWindow();

        //  window.setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.8f;    // 设置透明度为0.5
        window.setAttributes(lp);


        WindowManager.LayoutParams params = window.getAttributes();
        //params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
        window.setAttributes(params);
        ///

        dialog.show();
        hasshowdialog=true;
        dialog.getWindow().setContentView(contentView);

    }
}

package com.e21cn.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.e21cn.module.ToolRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by liangminqiang on 2018/7/31.
 */

public class Update {
    public static  long lasttime=0;
    public void updateSoftware(final Activity activity) {
        if(lasttime!=0&&(System.currentTimeMillis()-lasttime)<60000)
            return;
        lasttime=System.currentTimeMillis();
        Call<ResponseBody> call = ToolRequest.update();


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String result = response.body().string();
                    Log.d(Contacts.TAG, response.body().toString());
                    JSONObject jb = new JSONObject(result);
                    if (jb.has("version")) {
                        int version = Integer.valueOf(jb.getString("version"));
                        int localversion = activity.getPackageManager().
                                getPackageInfo(activity.getPackageName(), 0).versionCode;
                        if (version > localversion) {
                            //提示有更新
                            //showupdate(jb.getString("update_url"));
                            String installurl="itms-services://?action=download-manifest&url="+jb.getString("install_url");
                            //  showPopupWindow_Version(jb.getString("install_url"));
                          //  showUpdateDialog(activity,jb.getString("install_url"));
                            showDownloadProgressDialog(activity,jb.getString("install_url"));
                            //showPopupWindow_Version(jb.getString("update_url"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                // hidProDialog();
                //showToast("请求失败！");
                t.printStackTrace();
            }
        });
    }
    public void showUpdateDialog(final Activity activity, final String updateurl){
        CustormDialog custormDialog=new CustormDialog(activity,new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {


                  showDownloadProgressDialog(activity,updateurl);
                    // hasshowdialog=false;
                }catch (Exception e){
                    e.printStackTrace();
                }

                // dialog.dismiss();
            }
        });
        custormDialog.showPopupWindow_Version(updateurl);

    }

    public  void showDownloadProgressDialog(Activity activity, String downloadUrl) {

        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        Window window=progressDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
        window.setAttributes(params);

        progressDialog.show();
       // String downloadUrl = "http://ac-edNxPKqQ.clouddn.com/80xxxxxxxebcefda.apk";
        new DownloadAPK(progressDialog,activity).execute(downloadUrl);

    }
    /**
     * 下载APK的异步任务

     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        Activity activity;
        File file;

        public DownloadAPK(ProgressDialog progressDialog,Activity activity) {
            this.progressDialog = progressDialog;
            this.activity=activity;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;



            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());

                String fileName = Environment.getExternalStorageDirectory().getPath() + "/kszj/kszjface.apk";
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(file);
            progressDialog.dismiss();
        }

        private void openFile(File file) {
            if (file!=null){

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                activity.startActivity(intent);
                activity.finish();

            }

        }
    }
}

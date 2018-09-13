package com.e21cn.baseapplication;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author Liangminqiang
 * @version 创建时间：2011-3-1 上午09:43:38
 * MyAcitivty类说明:Acitivty的子类 基础该类的子类必须实现onCreate 方法
 * 在该类中注册了一个BroadcastReceiver 用于接收退出消息
 * 在接收到消息之后结束自身
 */
public abstract class BaseActivity extends AppCompatActivity {
	public Context mcontext;
	public  Unbinder unbinder;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(exitre);
		super.onDestroy();
		unbinder.unbind();
	}

//	public   Observable.Transformer schedulersTransformer() {
//		return new Observable.Transformer() {
//			@Override
//			public Object call(Object observable)
//			{
//				return ((Observable) observable).subscribeOn(Schedulers.io()) .unsubscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread());
//			}
//		};
//	}
	public void showToast(String mes){
		Toast.makeText(BaseActivity.this,mes,Toast.LENGTH_SHORT).show();
	}
//	public View getContentView(int id){
//		return  View.inflate(this, id, null);
//	}

	private ProgressDialog pdialog ;
	public void showProDialog(final String mes) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				pdialog = new ProgressDialog(BaseActivity.this);
				pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pdialog.setTitle("");
				pdialog.setMessage(mes);
				pdialog.setIndeterminate(false);
				pdialog.setCancelable(true);
				Window window=pdialog.getWindow();
				WindowManager.LayoutParams params = window.getAttributes();
				//params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
				params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

						| View.SYSTEM_UI_FLAG_FULLSCREEN
						|View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
				window.setAttributes(params);


				pdialog.show();
				Looper.loop();
			}
		});
		t.start();
	}
	public void hidProDialog(){
		if(pdialog!=null){
			pdialog.cancel();
			pdialog.cancel();
		}
	}
	private ExitListenerReceiver exitre;




	/**
	 * 设置Activity 的ContentView
	 */
	public abstract  int setContentView();
	/**
	 * 负责各个具体 Activity 初始化操作的显示
	 */
	public abstract void initView();

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				System.out.println(ex.getLocalizedMessage());
				finish();//关闭后台
				System.exit(0);
			}
		});
//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
//		Window win = getWindow();
//		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		win.requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//hideVirtualKey();
		//APPTool.hideBottomUIMenu(BaseActivity.this);影藏底部虚拟按键
		//hideBottomUIMenu();
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mcontext=BaseActivity.this;

		setContentView(setContentView());
		//OnsetContentView();
		 unbinder=ButterKnife.bind(this);
		initView();
		RegListener();
	}
	public static  void hideBottomUIMenu(Activity activity){
		Window window = activity.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		//params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

				| View.SYSTEM_UI_FLAG_FULLSCREEN
				|View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
		window.setAttributes(params);
	}
	/**
	 * 隐藏Android底部的虚拟按键
	 */
	private void hideVirtualKey(){

		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		//params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
		window.setAttributes(params);


	}
	/**
	 * 隐藏虚拟按键，并且全屏
	 */
	protected void hideBottomUIMenu() {
		//隐藏虚拟按键，并且全屏
		try {
			if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
				View v = this.getWindow().getDecorView();
				v.setSystemUiVisibility(View.GONE);
			} else if (Build.VERSION.SDK_INT >= 19) {

				//for new api versions.
				View decorView = getWindow().getDecorView();
				int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
				decorView.setSystemUiVisibility(uiOptions);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 注册退出事件监听
	 *
	 */
	public void RegListener() {
		exitre = new ExitListenerReceiver();
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(this.getPackageName() + "."
				+ "ExitListenerReceiver");
		this.registerReceiver(exitre, intentfilter);

	}

	class ExitListenerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {

			//完全退出方法


			BaseActivity.this.finish();
			((Activity) arg0).finish();

			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);

		}
	}
//	public boolean hasNet(){
//		boolean hasnet = false;
//		ConnectivityManager conMan = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		//mobile 3G Data Network
//
//		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//
//		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//
//		if (mobile==State.CONNECTED||mobile==State.CONNECTING)
//
//			return true;
//
//		if (wifi==State.CONNECTED||wifi==State.CONNECTING)
//
//			return true;
//		//Toast.makeText(a, "没有可用的网络连接", Toast.LENGTH_SHORT).show();
//
//		return false;
//	}
//	public static boolean checkNetworkInfo(final Context a) {
//		boolean hasnet = false;
//		ConnectivityManager conMan = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		//mobile 3G Data Network
//
//		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//
//		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//
//		if (mobile==State.CONNECTED||mobile==State.CONNECTING)
//
//			return true;
//
//		if (wifi==State.CONNECTED||wifi==State.CONNECTING)
//
//			return true;
//		//Toast.makeText(a, "没有可用的网络连接", Toast.LENGTH_SHORT).show();
//
//		return false;
//	}


}
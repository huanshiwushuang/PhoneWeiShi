package com.guohao.phoneweishi;


import org.json.JSONException;
import org.json.JSONObject;

import com.guohao.Util.Data;
import com.guohao.Util.HttpUtil;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView versionCode;
	private TextView versionName;
	private RelativeLayout rootLayout;
	private Handler handler;
	private Runnable r;
	private String VERSION_UPDATE_ADDRESS = "http://80smovie.tk/update.json";
	private final int idProgressBar = 1;
	private final int idAllSize = 2;
	private final int idCurrentSize = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		PackageInfo info = getLocalVersionInfo();
		setLocalVersionInfo(info);
		if (isCheckUpdate()) {
			getNetworkVersionInfo();
		}else {
			new Handler().postDelayed(r, 3*1000);
		}
	}
	
	private Boolean isCheckUpdate() {
		SharedPreferences p = Util.getPreferences(MainActivity.this);
		return p.getBoolean(Data.K_Auto_Update, Data.V_Auto_Updtae_Yes);
	}

	private void initView() {
		versionCode = (TextView) findViewById(R.id.id_textview_versionCode);
		versionName = (TextView) findViewById(R.id.id_textview_versionName);
		rootLayout = (RelativeLayout) findViewById(R.id.id_relativelayout_root);
		r = new Runnable() {
			@Override
			public void run() {
				StartActivity.actionStart(MainActivity.this);
				finish();
			}
		};
		
		handler = new Handler() {
			double d_all_size = -1;
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case HttpUtil.GET_VERSION_INFO_OK:
					String json = String.valueOf(msg.obj);
					try {
						JSONObject object = new JSONObject(json);
						if (Integer.valueOf(object.getString("versionCode")) > getLocalVersionInfo().versionCode) {
							updateVersion(object);
						}else {
							StartActivity.actionStart(MainActivity.this);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case HttpUtil.GET_VERSION_INFO_ERROR:
					Util.showToast(MainActivity.this, String.valueOf(msg.obj));
					StartActivity.actionStart(MainActivity.this);
					finish();
					break;
				case HttpUtil.GET_NETWORK_FILE_ERROR:
					Util.showToast(MainActivity.this, String.valueOf(msg.obj));
					finish();
					break;
				case HttpUtil.GET_NETWORK_FILE_OK:
					String savePath = String.valueOf(msg.obj);
					Util.showToast(MainActivity.this, "下载成功");
					break;
				case HttpUtil.GET_NETWORK_FILE_ALL_SIZE:
					TextView allSize = (TextView) findViewById(idAllSize);
					d_all_size = Double.parseDouble(String.valueOf(msg.obj));
					
					double showAllSize = Math.round(d_all_size/1024.0*100)/100.0;
					if (showAllSize < 1) {
						allSize.setText("总大小："+Math.round(d_all_size*100)/100.0+"KB");
					}else {
						allSize.setText("总大小："+showAllSize+"MB");
					}
					break;
				case HttpUtil.GET_NETWORK_FILE_CURRENT_SIZE:
					TextView currentSize = (TextView) findViewById(idCurrentSize);
					ProgressBar bar = (ProgressBar) findViewById(idProgressBar);
					double d_current_size = Double.parseDouble(String.valueOf(msg.obj));
					
					int p = (int)(d_current_size/d_all_size*100);
					bar.setProgress(p);
					double showCurrentSize = Math.round(d_current_size/1024.0*100)/100.0;
					if (showCurrentSize < 1) {
						currentSize.setText("已下载："+Math.round(d_current_size*100)/100.0+"KB");
					}else {
						currentSize.setText("已下载："+showCurrentSize+"MB");
					}
					break;
				default:
					break;
				}
			}
		};
	}

	private PackageInfo getLocalVersionInfo() {
		PackageManager manager = getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private void setLocalVersionInfo(PackageInfo info) {
		versionCode.setText("版本号："+info.versionCode+".0");
		versionName.setText("版本名："+info.versionName);
	}

	private void getNetworkVersionInfo() {
		if (!Util.getActiveNetworkInfo(MainActivity.this).isAvailable()) {
			finish();
			Util.showToast(MainActivity.this, "无可用网络");
			return;
		}
		HttpUtil.getVersionInfo(VERSION_UPDATE_ADDRESS, handler);
	}

	protected void updateVersion(final JSONObject object) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		try {
			builder.setTitle("最新版本："+object.getString("versionName"));
			builder.setMessage(object.getString("description"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		builder.setCancelable(false);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
				Util.showToast(MainActivity.this,"正在下载...");
				//tia
				
				//添加 ProgressBar 
				ProgressBar progressBar = new ProgressBar(MainActivity.this, null, android.R.attr.progressBarStyleHorizontal);
				progressBar.setIndeterminate(false);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.setId(idProgressBar); 
				
				DisplayMetrics metrics = Util.getScreenInfo(MainActivity.this);
				
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.setMargins(metrics.widthPixels/7, metrics.heightPixels/7*2, metrics.widthPixels/7, 0);
				rootLayout.addView(progressBar, params);
				
				//添加 TextView ，显示总大小。
				TextView allSize = new TextView(MainActivity.this);
				allSize.setId(idAllSize);
				allSize.setText("loading...");
				
				params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.BELOW, idProgressBar);
				params.addRule(RelativeLayout.ALIGN_RIGHT, idProgressBar);
				rootLayout.addView(allSize, params);
				
				//添加 TextView ，显示当前大小。
				TextView currentSize = new TextView(MainActivity.this);
				currentSize.setId(idCurrentSize);
				currentSize.setText("loading...");
				
				params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.BELOW, idProgressBar);
				params.addRule(RelativeLayout.ALIGN_LEFT, idProgressBar);
				rootLayout.addView(currentSize, params);
				
				try {
					String downUrl = object.getString("downloadUrl");
					String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+StringUtil.getFileNameFromUrl(downUrl);
					Util.showToast(MainActivity.this, "下载："+StringUtil.getFileNameFromUrl(downUrl));
					HttpUtil.getNetworkFile(downUrl,savePath,handler);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				StartActivity.actionStart(MainActivity.this);
				finish();
			}
		});
		builder.show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(r);
	}

}


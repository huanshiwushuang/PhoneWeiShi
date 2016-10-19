package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;
import com.guohao.custom.GridViewAdapter;
import com.guohao.receiver.MyAdmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class StartActivity extends Activity implements OnItemClickListener,OnClickListener {
	private GridView gridView;
	private EditText pwd1;
	private EditText pwd2;
	private TextView dialogTitle;
	private Button ok,cancle;
	private AlertDialog dialog;
	private ComponentName name;
	private	DevicePolicyManager policyManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		initView();
		
	}
	
	private void initView() {
		int[] imgId = 
				{R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
				R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
				R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings};
		String[] text = {"手机防盗","通讯卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
		
		gridView = (GridView) findViewById(R.id.id_gridview);
		GridViewAdapter adapter = new GridViewAdapter(StartActivity.this,imgId,text);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, StartActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			SharedPreferences preferences = Util.getPreferences(StartActivity.this);
			String pwd = preferences.getString(Data.K_Phone_Pwd, Data.V_Phone_Pwd_Default);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
			View v = LayoutInflater.from(StartActivity.this).inflate(R.layout.custom_input_pwd, null);
			dialogTitle = (TextView) v.findViewById(R.id.id_textview_title);
			pwd1 = (EditText) v.findViewById(R.id.id_edittext_pwd1);
			pwd2 = (EditText) v.findViewById(R.id.id_edittext_pwd2);
			ok = (Button) v.findViewById(R.id.id_button_ok);
			cancle = (Button) v.findViewById(R.id.id_button_cancle);
			
			ok.setOnClickListener(this);
			cancle.setOnClickListener(this);
			
			if (pwd.equals(Data.V_Phone_Pwd_Default)) {
				pwd2.setVisibility(View.VISIBLE);
				dialogTitle.setText("设置密码");
			}else {
				pwd2.setVisibility(View.GONE);
				dialogTitle.setText("输入密码");
			}
			builder.setView(v);
			builder.setCancelable(false);
			dialog = builder.show();
			break;
		case 1:
			LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				Util.showToast(StartActivity.this, "网络定位不可用");
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}else {
				LocationActivity.actionStart(StartActivity.this);
			}
			break;
		case 2:
			policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
			name = new ComponentName(this, MyAdmin.class);
			if (policyManager.isAdminActive(name)) {
				Log.d("guohao", "有管理员权限");
				//锁屏
				policyManager.lockNow();
				//重置密码
//				policyManager.resetPassword("guohao123", 0);
				//回复出厂设置
//				policyManager.wipeData(0);
			}else {
				Log.d("guohao", "没有管理员权限");
				Intent intent = new Intent();
				intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, name);
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "我需要这些权限");
				startActivityForResult(intent, 0);
				Log.d("guohao", "没有管理员权限");
			}
			break;
		case 3:
			if (name != null && policyManager.isAdminActive(name)) {
				policyManager.removeActiveAdmin(name);
			}
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_DELETE);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.setData(Uri.parse("package:"+getPackageName()));
			startActivity(intent);
			break;
		case 8:
			SettingActivity.actionStart(StartActivity.this);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				Util.showToast(StartActivity.this, "ok");
			}else {
				Util.showToast(StartActivity.this, "cancle");
			}
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_button_ok:
			SharedPreferences p = Util.getPreferences(StartActivity.this);
			if (pwd2.getVisibility() == View.GONE) {
				String string = p.getString(Data.K_Phone_Pwd, Data.V_Phone_Pwd_Default);
				String pwd = StringUtil.encodeMD5(pwd1.getText().toString());
				if (pwd.equals(string)) {
					dialog.dismiss();
					PhoneSecurity01.actionStart(StartActivity.this);
					Util.showToast(StartActivity.this, "登陆成功");
				}else {
					Util.showToast(StartActivity.this, "密码错误");
				}
			}else {
				if (StringUtil.isEmpty(pwd1.getText().toString())) {
					Util.showToast(StartActivity.this, "密码不能为空");
				}else if (!pwd1.getText().toString().equals(pwd2.getText().toString())) {
					Util.showToast(StartActivity.this, "密码不相同");
				}else {
					String pwd = StringUtil.encodeMD5(pwd1.getText().toString());
					Editor editor = p.edit();
					editor.putString(Data.K_Phone_Pwd, pwd);
					editor.commit();
					dialog.dismiss();
					Util.showToast(StartActivity.this, "设置成功");
				}
			}
			break;
		case R.id.id_button_cancle:
			if (dialog != null) {
				dialog.dismiss();
			}
			break;
		}
	}
}

package com.guohao.phoneweishi;

import com.guohao.Util.Data;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;
import com.guohao.custom.GridViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
		case 8:
			SettingActivity.actionStart(StartActivity.this);
			break;
		default:
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
					PhoneSecurity01.actionStart(StartActivity.this,Data.V_Phone_SIM_Serial,Data.V_Phone_Security_Num,Data.V_Phone_Security_Start);
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

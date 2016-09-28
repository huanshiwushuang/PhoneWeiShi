package com.guohao.phoneweishi;

import java.util.ArrayList;
import java.util.List;

import com.guohao.Util.Data;
import com.guohao.Util.StringUtil;
import com.guohao.Util.Util;
import com.guohao.adapter.AdapterSelectPhoneNum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class PhoneSecurity03 extends BaseActivityPhoneSecuritySetting implements OnClickListener,OnItemClickListener {
	private EditText inputPhoneNum;
	private Button selectPhoneNum;
	
	private SharedPreferences p;
	private AlertDialog.Builder builder;
	private AlertDialog dialog;
	//联系人数据
	private List<String[]> lists;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonesecurity03);
		
		initView();
		initShareData();
		setListener();
	}
	private void setListener() {
		selectPhoneNum.setOnClickListener(this);
	}
	private void initShareData() {
		String serial = p.getString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		if (!serial.equals(Data.V_Phone_Security_Num)) {
			inputPhoneNum.setText(serial);
		}
	}
	private void initView() {
		inputPhoneNum = (EditText) findViewById(R.id.id_edittext_input_phone_num);
		selectPhoneNum = (Button) findViewById(R.id.id_button_select_phone_num);
		p = Util.getPreferences(this);
		builder = new AlertDialog.Builder(PhoneSecurity03.this);
	}
	@Override
	public void onClick(View v) {
		lists = new ArrayList<String[]>();
		//查询---所有联系人数据
		Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				lists.add(new String[]{name,num});
			}
		}
		//构造弹出框
		View view = LayoutInflater.from(PhoneSecurity03.this).inflate(R.layout.select_phone_num, null);
		builder.setTitle("选择安全号码");
		builder.setView(view);
		//适配数据
		ListView l = (ListView) view.findViewById(R.id.id_listview_select_phone_num);
		l.setOnItemClickListener(this);
		AdapterSelectPhoneNum adapter = new AdapterSelectPhoneNum(PhoneSecurity03.this, R.layout.select_phone_num_item, lists);
		l.setAdapter(adapter);
		dialog = builder.show();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String num = lists.get(position)[1];
		if (dialog != null) {
			dialog.dismiss();
		}
		Util.showToast(PhoneSecurity03.this, "你选择了（"+lists.get(position)[0]+"）的号码");
		inputPhoneNum.setText(num);
	}
	//------------------------------------------------------------------------------
	public void next(View view) {
		saveShareData();
		PhoneSecurity04.actionStart(PhoneSecurity03.this);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	public void last(View view) {
		saveShareData();
		PhoneSecurity02.actionStart(PhoneSecurity03.this);
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	private void saveShareData() {
		String securityNum = inputPhoneNum.getText().toString();
		Editor editor = p.edit();
		if (StringUtil.isEmpty(securityNum)) {
			editor.putString(Data.K_Phone_Security_Num, Data.V_Phone_Security_Num);
		}else {
			editor.putString(Data.K_Phone_Security_Num, securityNum);
		}
		editor.commit();
	}
	public static void actionStart(Context c) {
		Intent intent = new Intent(c, PhoneSecurity03.class);
		c.startActivity(intent);
	}
}

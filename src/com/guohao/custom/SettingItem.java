package com.guohao.custom;

import com.guohao.Util.Util;
import com.guohao.phoneweishi.R;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItem extends RelativeLayout implements OnClickListener {
	private TextView textView;
	private CheckBox checkBox;

	public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public SettingItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SettingItem(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.custom_setting_item, this);
		textView = (TextView) findViewById(R.id.id_textview_update_prompt);
		checkBox = (CheckBox) findViewById(R.id.id_checkbox_status);
		checkBox.setClickable(false);
		this.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		checkBox.setChecked(!checkBox.isChecked());
		Editor editor = Util.getPreferences(getContext()).edit();
		if (checkBox.isChecked()) {
			editor.putBoolean(com.guohao.Util.Data.K_Auto_Update, com.guohao.Util.Data.V_Auto_Updtae_Yes);
			textView.setText("自动更新已经开启");
		}else {
			editor.putBoolean(com.guohao.Util.Data.K_Auto_Update, com.guohao.Util.Data.V_Auto_Updtae_No);
			textView.setText("自动更新已经关闭");
		}
		editor.commit();
	}
	
	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}
}

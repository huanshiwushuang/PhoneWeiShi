package com.guohao.custom;

import com.guohao.phoneweishi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewDeng extends TextView {

	
	public TextViewDeng(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public TextViewDeng(Context context) {
		super(context);
	}
	
	public TextViewDeng(Context context, AttributeSet attrs) {
		super(context, attrs);
//		//��ȡ�Զ��������
//		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);
//		array.getInt(R.styleable.ToolBar_numValue,-1);
//		array.getResourceId(R.styleable.ToolBar_btnBg, -1);
//		array.recycle();//�ͷ���Դ
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}

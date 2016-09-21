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
//		//获取自定义的属性
//		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);
//		array.getInt(R.styleable.ToolBar_numValue,-1);
//		array.getResourceId(R.styleable.ToolBar_btnBg, -1);
//		array.recycle();//释放资源
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}

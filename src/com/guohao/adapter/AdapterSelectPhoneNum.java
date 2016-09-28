package com.guohao.adapter;

import java.util.List;

import com.guohao.phoneweishi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterSelectPhoneNum extends ArrayAdapter<String[]> {
	private LayoutInflater inflater;
	private int mResource;
	private List<String[]> ls;

	public AdapterSelectPhoneNum(Context context, int resource, List<String[]> objects) {
		super(context, resource, objects);
		inflater = LayoutInflater.from(context);
		mResource = resource;
		ls = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(mResource, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.id_textview_name); 
			holder.num = (TextView) convertView.findViewById(R.id.id_textview_num);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(ls.get(position)[0]);
		holder.num.setText(ls.get(position)[1]);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView name;
		TextView num;
	}
}

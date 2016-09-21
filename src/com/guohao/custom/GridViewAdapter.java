package com.guohao.custom;

import com.guohao.phoneweishi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private int[] imgId;
	private String[] text;
	private Context context;
	
	public GridViewAdapter(Context context, int[] imgId, String[] text) {
		this.imgId = imgId;
		this.text = text;
		this.context = context;
	}

	@Override
	public int getCount() {
		return imgId.length;
	}

	@Override
	public Object getItem(int position) {
		return text[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.id_imageview);
			holder.text = (TextView) convertView.findViewById(R.id.id_textview);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.img.setImageResource(imgId[position]);
		holder.text.setText(text[position]);
		return convertView;
	}
	
	class ViewHolder {
		ImageView img;
		TextView text;
	}
}

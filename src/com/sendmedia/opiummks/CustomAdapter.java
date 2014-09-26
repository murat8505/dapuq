package com.sendmedia.opiummks;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	Context context;
	List<RowItem> rowItem;
	
	private int selectedItemPosition = -1;

	CustomAdapter(Context context, List<RowItem> rowItem) {
		this.context = context;
		this.rowItem = rowItem;
	}

	private class ViewHolder {
		ImageView icon;
		TextView title;
	}
	
	
	//get selected position
	public void selectItem(int position) {
	    selectedItemPosition = position;
	  }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		convertView = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			//holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.title = (TextView) convertView.findViewById(R.id.title);

			RowItem row_pos = rowItem.get(position);
			// setting the image resource and title
			//holder.icon.setImageResource(row_pos.getIcon());
			holder.title.setText(row_pos.getTitle());
			convertView.setTag(holder);

		}
		//get selected position
		if (position == selectedItemPosition){
			holder.title.setBackgroundResource(R.drawable.bg_list_selected);
			holder.title.setTypeface(null,Typeface.BOLD);
			holder.title.setPadding(15, 18, 0, 18);
		} else {
			holder.title.setBackgroundResource(R.drawable.bg_list_normal);
		}
		
		return convertView;

	}

	@Override
	public int getCount() {
		return rowItem.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItem.indexOf(getItem(position));
	}

}

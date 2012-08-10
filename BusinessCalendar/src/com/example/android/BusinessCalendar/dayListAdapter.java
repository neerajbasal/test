package com.example.android.BusinessCalendar;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;




public class dayListAdapter extends BaseAdapter
{
		private static ArrayList<EventDetails> eventArrayList;
		String strDate,strtDate;
		
	 
	 private LayoutInflater mInflater;

	 public dayListAdapter(Context context, ArrayList<EventDetails> events) 
	 {
		  eventArrayList = events;
		  mInflater = LayoutInflater.from(context);
	 }
	 
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eventArrayList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return eventArrayList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		 ViewHolder holder;
		 
		  if (convertView == null) 
		  {
		   convertView = mInflater.inflate(R.layout.dayeventrowlayout, null);
		   holder = new ViewHolder();
		   holder.txtName = (TextView) convertView.findViewById(R.id.dayEventName);
		    
		   convertView.setTag(holder);
		  } 
		  else 
		  {
		   holder = (ViewHolder) convertView.getTag();
		  }	    
			  holder.txtName.setText(eventArrayList.get(position).getTitle());
		
		  return convertView;
	}

	 static class ViewHolder 
	 {
	  TextView txtName;
	 }

}

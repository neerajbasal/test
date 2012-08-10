package com.example.android.BusinessCalendar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;




public class ListAdapter extends BaseAdapter
{
		private static ArrayList<EventDetails> eventArrayList;
		String strDate,strtDate;
	 
	 private LayoutInflater mInflater;

	 public ListAdapter(Context context, ArrayList<EventDetails> events) 
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
		 Long temp;
		 
		 int month,year,day,mmonth,myear,mday;
		 String date;
		 Calendar mCalendar = Calendar.getInstance();
		 month =mCalendar.get(Calendar.MONTH);
		 year = mCalendar.get(Calendar.YEAR);
		 day = mCalendar.get(Calendar.DAY_OF_MONTH);
		 
		 
		 
		  if (convertView == null) 
		  {
		   convertView = mInflater.inflate(R.layout.listrowlayout, null);
		   holder = new ViewHolder();
		   holder.txtName = (TextView) convertView.findViewById(R.id.eventName);
		   holder.txtDate = (TextView) convertView.findViewById(R.id.eventDate);
		   holder.txtLocation = (TextView) convertView.findViewById(R.id.eventLocation);
		    
		   convertView.setTag(holder);
		  } 
		  else 
		  {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  temp = eventArrayList.get(position).getStartDate();
		  Date dte = new Date(temp);
		  mmonth= dte.getMonth();
		  myear = dte.getYear();
		  mday = dte.getDay();
		    
			  holder.txtName.setText(eventArrayList.get(position).getTitle());
			  holder.txtDate.setText(eventArrayList.get(position).getStartDateStr());
			  holder.txtLocation.setText(eventArrayList.get(position).getLocation());  
		
		  return convertView;
	}

	 static class ViewHolder 
	 {
	  TextView txtName;
	  TextView txtDate;
	  TextView txtLocation;
	 }

}

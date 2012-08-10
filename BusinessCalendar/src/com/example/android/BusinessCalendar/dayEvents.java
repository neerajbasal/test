package com.example.android.BusinessCalendar;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class dayEvents extends Activity 
{
	Context mContext;
	int year,month,position;
	int date;
	ArrayList<EventDetails> eventDataArr = new ArrayList<EventDetails>();
	ArrayList<EventDetails> events = new ArrayList<EventDetails>();
	String title,location,description;
	Long startdate,enddate;
	int eventid;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dayeventlist);
		
		mContext=this;
		initCalendarDB();
		
		Intent intent = getIntent();
		
		if(intent.getExtras()!= null)
		{
			year=intent.getExtras().getInt("year");
			month=intent.getExtras().getInt("month");
			position=intent.getExtras().getInt("day");
			date=intent.getExtras().getInt("date");
			
		}
		events = checkEventOnDate(setStartDate(date),eventDataArr);
		
		dayListAdapter daylist = new dayListAdapter(mContext,events);
		ListView lv = (ListView)findViewById(R.id.list);
		lv.setAdapter(daylist);

		lv.setOnItemClickListener(new OnItemClickListener ()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
			{	
				
				title=events.get(position).getTitle();
				location=events.get(position).getLocation();
				description=events.get(position).getDescription();
				startdate=events.get(position).getStartDate();
				enddate=events.get(position).getEndDate();
				

				Intent intent = new Intent(dayEvents.this , editEvents.class);
				 intent.putExtra("title",title);
			        intent.putExtra("location",location);
			        intent.putExtra("description",description);
			        intent.putExtra("year", year);
			    	intent.putExtra("month",month);
			    	intent.putExtra("date",date);
				startActivity(intent);
				finish();
			
			}
			
		});
		
	}
	
	
				
	   public  ArrayList<EventDetails> checkEventOnDate(String cellDate,ArrayList<EventDetails> eventDataArray)
	   {
		   ArrayList<EventDetails> eD = new ArrayList<EventDetails>();
		   for(int i=0;i<eventDataArray.size();i++)
		   {
			   if(cellDate.equalsIgnoreCase(eventDataArray.get(i).getStartDateStr()))
			   {
				   eD.add(eventDataArray.get(i));

			   }
		   }
		   return eD;
	   } 
	   
	   public String setStartDate(int day) 
	   {
			
			String strDate = String.valueOf(year ) + "-"
					+ String.valueOf(month + 1) + "-"
					+ String.valueOf(day);
			return strDate;
		}
	  
	
	  
	    private void initCalendarDB()
	    {
	    	int cal_id=1;
	    	
			 ContentResolver cr = mContext.getContentResolver();
			 Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/events"), new String[]{ "calendar_id", "title", "description", "dtstart", "dtend","visibility", "eventLocation","allDay","hasAlarm","eventStatus"}, "calendar_id=" + cal_id, null,"dtstart DESC");         
			    //Cursor cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "name" }, null, null, null);
	    
			 
			 	cursor.moveToFirst();
			    for (int i = 0; i < cursor.getCount(); i++) 
			    { 	
			   		EventDetails eventData= new EventDetails();
			    		eventData.setTitle( cursor.getString(1));
			    		eventData.setDescription(cursor.getString(2));
			    		eventData.setLocation(cursor.getString(6));
			    		eventData.setStartDate(cursor.getLong(3));
			    		eventData.setEndDate(cursor.getLong(4));
			    		eventData.setVisibility(cursor.getInt(5));
			    		eventData.setAvailability(cursor.getInt(9));
			    		eventDataArr.add(eventData);
			    		cursor.moveToNext();	
			    
			    }
			    cursor.close();
		 }
	    public void getValues(int position,Integer[] dates,int month,int year)
	    {
	    	this.position=position;
	    	this.year=year;
	    	this.month=month;
	    }

}

package com.example.android.BusinessCalendar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class widgetActivity extends AppWidgetProvider
{
	Context mContext;
	int month,year,date;
	int mmonth,myear,mdate;
	String gDate;
	TextView txtview;

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds)
    {
    	mContext = context;
    	initCalendarDB();
    
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++)
        {
        	 int appWidgetId = appWidgetIds[i];
             CharSequence text = "No events";
             Calendar mCalendar = Calendar.getInstance();
     			month =mCalendar.get(Calendar.MONTH);
     			year = mCalendar.get(Calendar.YEAR);
     			date = mCalendar.get(Calendar.DAY_OF_MONTH);
     			String strDate = String.valueOf(year) + "-"
 						+ String.valueOf(month + 1) + "-"
 						+ String.valueOf(date);
     			
     			Intent intent = new Intent(context, calpage.class);
     			PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, 0);
     			
     		   RemoteViews views = new RemoteViews(context.getPackageName(),
                       R.layout.appwidget);
            
     		   
     			for(int j = 0;j<eventDataArr.size();j++)
     			{
     				Long strdate;
     				strdate = eventDataArr.get(j).getStartDate();
     				Date sdate = new Date(strdate);
     				myear=sdate.getYear() + 1900;
     				mmonth =sdate.getMonth() + 1;
     				mdate = sdate.getDate();
     				
     				if(year == myear && mmonth >= (month+1) && mdate >= date)
     				{
     					gDate = eventDataArr.get(j).getStartDateStr();
     					text = eventDataArr.get(j).getTitle();
     					break;
     				}
     				
     			}
     			views.setTextViewText(R.id.appwidget_text_header,strDate);
     			views.setTextViewText(R.id.appwidget_text," "+gDate+"\n"+ text);
     			
    			
    			views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);	  
             appWidgetManager.updateAppWidget(appWidgetId, views);
        }
       
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
	 
	ArrayList<EventDetails> eventDataArr = new ArrayList<EventDetails>();
    private void initCalendarDB()
    {
		 ContentResolver cr = mContext.getContentResolver();
		 Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/events"), new String[]{ "calendar_id", "title", "description", "dtstart", "dtend","visibility", "eventLocation","allDay","hasAlarm","eventStatus"},null, null,"dtstart ASC");         
		    //Cursor cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "name" }, null, null, null);
		    cursor.moveToFirst();
		    for (int i = 0; i < cursor.getCount(); i++) {
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

    
}
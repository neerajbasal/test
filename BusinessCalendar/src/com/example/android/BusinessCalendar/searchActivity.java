package com.example.android.BusinessCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class searchActivity extends Activity
{
	
	Button searchbtn;
	Context mContext = this;
	ArrayList<String> eD = new ArrayList<String>();
	ArrayList<String> eDTitle = new ArrayList<String>();
	ArrayList<String> eDLocation = new ArrayList<String>();
	ArrayList<String> eDDescription = new ArrayList<String>();
	String title,location,description;
	Long startdate,enddate;
	AutoCompleteTextView searchtext;
	ListView searchlist;
	String text;
	ArrayList<EventDetails> searchevent;
	int year,month,dates;
	 Dialog helpdialog;
	 Button helpokbtn;
	 Menu menu;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlayout);
		initCalendarDB();
		
		
		for(int i=0;i<eventDataArr.size();i++)
	 	   {
	 			  eDTitle.add(eventDataArr.get(i).getTitle());
	 			  eDLocation.add(eventDataArr.get(i).getLocation());
	 			  eDDescription.add(eventDataArr.get(i).getDescription());
	 	   }
		
		
		for(int i=0;i<eDTitle.size();i++)
		{
			eD.add(eDTitle.get(i));
			eD.add(eDLocation.get(i));
			eD.add(eDDescription.get(i));
		}

	
		 searchtext = (AutoCompleteTextView) findViewById(R.id.autocomplete_search);
		 ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.list_item,eD);
		 searchtext.setAdapter(adapter);
		 
		
		 searchevent = new ArrayList<EventDetails>();
		
		 searchbtn = (Button) findViewById(R.id.btn_search);
		 searchbtn.setOnClickListener(new OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{	
				
				 
				int j = 0;
				int flag = 0;
				for(int i=0;i<eDTitle.size();i++)
				{
					if(eDTitle.get(i).equals(searchtext.getText().toString()))
					{
						j = i;
						flag = 1;
						searchevent.add(eventDataArr.get(j));
					}
					if(eDLocation.get(i).equals(searchtext.getText().toString()))
					{
						j = i;
						flag = 1;
						searchevent.add(eventDataArr.get(j));
					}
					if(eDDescription.get(i).equals(searchtext.getText().toString()))
					{
						j = i;
						flag = 1;
						searchevent.add(eventDataArr.get(j));
					}
				}
			
				
				
				if(flag == 1)
				{
					searchlist = (ListView) findViewById(R.id.searchlist);
					ListAdapter adapter = new ListAdapter(mContext,searchevent);
					searchlist.setAdapter(adapter);
					
					searchlist.setOnItemClickListener(new OnItemClickListener ()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
						{	
							
							title=searchevent.get(position).getTitle();
							location=searchevent.get(position).getLocation();
							description=searchevent.get(position).getDescription();
							startdate=searchevent.get(position).getStartDate();
							enddate=searchevent.get(position).getEndDate();
							
							Date date = new Date(startdate);
							year = Integer.valueOf(date.getYear() + 1900);
							month = Integer.valueOf(date.getMonth());
							dates = Integer.valueOf(date.getDate());
							
							

							Intent intent = new Intent(searchActivity.this , editEvents.class);
							 intent.putExtra("title",title);
						        intent.putExtra("location",location);
						        intent.putExtra("description",description);
						        intent.putExtra("year", year);
						    	intent.putExtra("month",month);
						    	intent.putExtra("date",dates);
							startActivity(intent);
							finish();
						}
						
					});

					
				}
				
								
			}	 
		 });
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater newmenu = getMenuInflater();
    	newmenu.inflate(R.menu.agenda_menu, menu);
    	this.menu = menu;
    	return true;
    	
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{		
    		case R.id.addevent:
    			
    			  Calendar mCalendar = Calendar.getInstance();
    				int mmonth =mCalendar.get(Calendar.MONTH);
    				int myear = mCalendar.get(Calendar.YEAR);
    				int mdate = mCalendar.get(Calendar.DAY_OF_MONTH);
    			
    				Intent intent = new Intent(searchActivity.this,eventmanager.class);
					intent.putExtra("date",mdate );
					intent.putExtra("month",mmonth);
					intent.putExtra("year",myear);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);
    			
    			return true;	
    		case R.id.monthview :
    			
    			Intent intent1 = new Intent(searchActivity.this,calpage.class);
    			startActivity(intent1);
    			finish();
    			return true;
    			
    		case R.id.agendacalendars:
    			
    			startActivity(new Intent(searchActivity.this,calendars.class));
    			return true;
    			
    	}
    	return false;
    }
	
	  ArrayList<EventDetails> eventDataArr = new ArrayList<EventDetails>();
	    
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
	
}


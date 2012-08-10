package com.example.android.BusinessCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.android.Adapter.SeparatedListAdapter;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;




public class getAgenda extends Activity
{
	 Context mContext;
	 
	 int size,msize;
	 
	 Menu menu;
	 
	 String[] dates;
	 
	 Dialog helpdialog;
	 
	 Button helpokbtn;
	 
	 public final static String ITEM_TITLE = "title";
     public final static String ITEM_CAPTION = "caption";

     // Adapter for ListView Contents
     private SeparatedListAdapter adapter;

     // ListView Contents
     private ListView journalListView;
      
     ArrayList<String> eventDataCell = new ArrayList<String>();
     
     ArrayAdapter<String> listadapter;

     public Map<String, ?> createItem(String title, String caption)
     	{
    	 	Map<String, String> item = new HashMap<String, String>();
    	 	item.put(ITEM_TITLE, title);
    	 	item.put(ITEM_CAPTION, caption);
    	 	return item;
     	}

	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		
		mContext=this;
		
		initCalendarDB();
		getSize();
		
		msize = eventDataArr.size()-size+1;
		dates = new String[msize];
		
		getDates();
		
	    setContentView(R.layout.agendalayout);
		adapter = new SeparatedListAdapter(this);
	  
		
        // Add Sections
		for (int i = 0; i < dates.length; i++)
        	{
				eventDataCell =checkEventOnDate(dates[i],eventDataArr);
				listadapter = new ArrayAdapter<String> (this,R.layout.list_item,eventDataCell);
				adapter.addSection(dates[i], listadapter);
        	}
 
          // Get a reference to the ListView holder
          journalListView = (ListView) this.findViewById(R.id.list_journal);

          // Set the adapter on the ListView holder
          journalListView.setAdapter(adapter);

          // Listen for Click events
          journalListView.setOnItemClickListener(new OnItemClickListener()
              {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
                      {
                          String item = (String) adapter.getItem(position);
                          Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
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
    			
    				Intent intent = new Intent(getAgenda.this,eventmanager.class);
					intent.putExtra("date",mdate );
					intent.putExtra("month",mmonth);
					intent.putExtra("year",myear);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);
    			
    			return true;	
    		case R.id.monthview :
    			
    			Intent intent1 = new Intent(getAgenda.this,calpage.class);
    			startActivity(intent1);
    			finish();
    			return true;
    			
    		case R.id.agendacalendars:
    			
    			startActivity(new Intent(getAgenda.this,calendars.class));
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
    
    public void getDates()
    {
    	String temp;
    	int j=0;
    	int k;
    	k = eventDataArr.size()-1;
    	temp=eventDataArr.get(k).getStartDateStr();
    	dates[0]=eventDataArr.get(k).getStartDateStr();
    	j++;
    	
    	for(int i=(eventDataArr.size()-1);i>=0;i--)
    	{
    		if(eventDataArr.get(i).getStartDateStr().equals(temp))
    		{	
    		}	
    		else
    		{
    			temp = eventDataArr.get(i).getStartDateStr();
    			dates[j]=eventDataArr.get(i).getStartDateStr();
    			j++;
    			k--;
    		}
    	}
    	
    }
    
    public void getSize()
    {
    	String temp;
    	size =0;
    	temp=eventDataArr.get(0).getStartDateStr();
    	
    	for(int i=0;i<eventDataArr.size();i++)
    	{
    		if(eventDataArr.get(i).getStartDateStr().equals(temp))
    		{	
    			size++;
    		}	
    		else
    		{
    			temp = eventDataArr.get(i).getStartDateStr();
    		}
    	}
    }
    
    private  ArrayList<String> checkEventOnDate(String cellDate,ArrayList<EventDetails> eventDataArray)
    {
 	   ArrayList<String> eD = new ArrayList<String>();
 	   for(int i=0;i<eventDataArray.size();i++)
 	   {
 		   if(cellDate.equalsIgnoreCase(eventDataArray.get(i).getStartDateStr()))
 		   {
 			  //  eD.add(eventDataArray.get(i));
 			   eD.add(eventDataArray.get(i).getTitle());
 		   }
 	   }
 	   return eD;
    }
   
}

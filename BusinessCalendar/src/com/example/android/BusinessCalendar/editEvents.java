package com.example.android.BusinessCalendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;

public class editEvents extends Activity
{
	int year,month,date;
	String title = null,location = null ,description = null;
	Long startdate = null,enddate = null;
	TextView edit_title,edit_location,edit_date,edit_description;
	Button alter,delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editevents);
		
		edit_title = (TextView) findViewById(R.id.textView_edit_title);
		edit_location = (TextView) findViewById(R.id.textView_edit_location);
		edit_description = (TextView) findViewById(R.id.textView_edit_description);
		edit_date = (TextView) findViewById(R.id.textView_edit_date);
		
		alter = (Button) findViewById(R.id.btn_alter);
		delete = (Button) findViewById(R.id.btn_delete);
		
        Intent intent = getIntent();
		if(intent.getExtras()!= null)
		{
			title=intent.getExtras().getString("title");
			location=intent.getExtras().getString("location");
			description=intent.getExtras().getString("description");
			year=intent.getExtras().getInt("year");
			month=intent.getExtras().getInt("month");
			date=intent.getExtras().getInt("date");
		}
		
		edit_title.setText(" "+title.toString());
		edit_location.setText(" "+location.toString());
		edit_description.setText(" "+description.toString());
		edit_date.setText(" "+setDate(date));
		
		
		edit_location.setClickable(true);
		edit_location.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(editEvents.this, displayLocation.class);
				intent.putExtra("location",location);
				startActivity(intent);
				
			}
			
		});
		delete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Uri CALENDAR_URI =Uri.parse("content://com.android.calendar/events");
				ContentResolver cr = getContentResolver();
				cr.delete(CALENDAR_URI,"calendar_id=? and title=? " ,new String[]{"1", String.valueOf(title)});
			
				Intent intent = new Intent(editEvents.this , calpage.class );
				intent.putExtra("day",date );
				intent.putExtra("month",month);
				intent.putExtra("year",year);	
				startActivity(intent);
				finish();
				
			}
			
			
		});
		alter.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(editEvents.this , eventmanager.class);
					intent.putExtra("flag",1);
					intent.putExtra("title",title);
					intent.putExtra("location",location);
			        intent.putExtra("description",description);
			        intent.putExtra("year", year);
			    	intent.putExtra("month",month);
			    	intent.putExtra("date",date);
				startActivity(intent);
				
			}	
		});

		
	}
	
public String setDate(int day)
{
		
		String strDate =
				String.valueOf(day)+" -"+ String.valueOf(month + 1) + "-"+String.valueOf(year );
		return strDate;
	}
	

}

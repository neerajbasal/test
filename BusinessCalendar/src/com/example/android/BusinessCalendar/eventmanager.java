package com.example.android.BusinessCalendar;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


public class eventmanager extends Activity 
{
	
	static int mn;
	static int yy;
	static int pos;
	static int date;
	static int HELLO_ID =1;
	int ddate,dmn,dyear;
	 Context mContext;
	 Dialog timedialog , datedialog; 
	 int dflag = 0,tflag =0;
	 Button btn_save,btn_cancel,btn_current_date,btn_current_time,btn_end_date,btn_end_time;
	 int mYear;
	  private int mMonth;
	    private int mDay;
	    static final int DATE_DIALOG_ID = 0;
	     private int mSelYear;
	     private int mSelMonth;
	     private int mSelDay;
	     private int mSelMinute;
	     private int mSelHour;
	    private int mHour;
	    private int mMinute;
	    private final long HALF_HOUR =1800000;
	    static final int TIME_DIALOG_ID = 1;
	    EditText title,startdate,enddate,location,description;
	    boolean bBtnCurrentTime,bBtnCurrentDate;
	     StringBuilder selectedDate,selectedTime;
	     CheckBox allday, hasalarm;
	     int alldayvalue = 0;
	     int alarmvalue = 0;
	     int alterflag = 0;
	     int visibilityvalue,availabilityvalue;
	 	String titletxt,locationtxt,descriptiontxt,availabilitytext,visibilitytext;
	 	Spinner privacy,availability,calendar;
	 	int currentYear,currentMonth;
	 	String cal_id;
	 	ArrayList<EventDetails> available ;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventpage);	

		mContext = this;
		Intent intent = getIntent();
		if (intent.getExtras() != null) 
		{
			mSelYear = intent.getExtras().getInt("year");
			mSelMonth = intent.getExtras().getInt("month");
			mSelDay = intent.getExtras().getInt("date");
			alterflag = intent.getExtras().getInt("flag");
			titletxt=intent.getExtras().getString("title");
			locationtxt=intent.getExtras().getString("location");
			descriptiontxt=intent.getExtras().getString("description");
	
		}
	
		setXmlData();
		setListener();
        setTime();
		
		//   getCalendars();
    	allday = (CheckBox) findViewById(R.id.check_allday);
    	hasalarm = (CheckBox) findViewById(R.id.check_reminder);
    	privacy = (Spinner) findViewById(R.id.spinner_privacy);
    	availability = (Spinner) findViewById(R.id.spinner_show_me);
    	calendar = (Spinner) findViewById(R.id.spinner_which_calendar);
    	
    	
    	
    	
	     title = (EditText) findViewById(R.id.edit_text_what);
		 location = (EditText) findViewById(R.id.edit_text_location);
		 description = (EditText) findViewById(R.id.edit_text_description);
		 
		 btn_current_time.setText("8:00 am");
		 btn_end_time.setText("10:00 pm");
		 
		 if(alterflag == 1)
		 {
			 title.setText(" "+titletxt);
			 location.setText(" "+locationtxt);
		 }
				    
	}

	  public void setXmlData()
		{
			    btn_save = (Button) findViewById(R.id.btn_eventSave);
	         	btn_cancel = (Button) findViewById(R.id.btn_eventCancel);
	         	btn_current_date = (Button) findViewById(R.id.btn_from_date);	
	         	btn_end_date = (Button) findViewById(R.id.btn_to_date);
	         	btn_current_time = (Button) findViewById(R.id.btn_from_time);
	         	btn_end_time = (Button) findViewById(R.id.btn_to_time);
	         	
	        	btn_current_date.setText( new StringBuilder()
	             .append(mSelMonth + 1).append("-")
	             .append(mSelDay).append("-")
	             .append(mSelYear).append(" ")
	            );
	        	btn_end_date.setText( new StringBuilder()
	             .append(mSelMonth + 1).append("-")
	             .append(mSelDay).append("-")
	             .append(mSelYear).append(" ")
	            );
	         	
		 }
	  
	  private void setListener(){
			
			 btn_current_date.setOnClickListener(new View.OnClickListener()
			 {

		            public void onClick(View v) {
		                showDialog(DATE_DIALOG_ID);
		                bBtnCurrentDate=true;
		            }
		        });
			 btn_current_time.setOnClickListener(new View.OnClickListener() {

		            public void onClick(View v) {
		                showDialog(TIME_DIALOG_ID);
		                bBtnCurrentTime=true;
		            }
		        });
			 btn_end_date.setOnClickListener(new View.OnClickListener() {

		            public void onClick(View v) {
		                showDialog(DATE_DIALOG_ID);
		            }
		        });
			 btn_end_time.setOnClickListener(new View.OnClickListener() {

		            public void onClick(View v) {
		                showDialog(TIME_DIALOG_ID);
		            }
		        });
			 btn_save.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) 
				{
					if(calendar.getSelectedItem().toString().equals("Phone"))
			    	{
			    		cal_id = "1";
			    	}
			    	else
			    	{
			    		cal_id = "2";
			    	}
					
					// TODO Auto-generated method stub
					if(alterflag == 1)
					{
						Uri CALENDAR_URI =Uri.parse("content://com.android.calendar/events");
						ContentResolver cr = getContentResolver();
						cr.delete(CALENDAR_URI,"calendar_id=? and title=? " ,new String[]{cal_id, String.valueOf(titletxt)});
					
						alterflag = 0;
					}
					
					visibilitytext = privacy.getSelectedItem().toString();
			    	availabilitytext = availability.getSelectedItem().toString();
			    	
			    	if(calendar.getSelectedItem().toString().equals("Phone"))
			    	{
			    		cal_id = "1";
			    	}
			    	else
			    	{
			    		cal_id = "2";
			    	}
			    	
					
					if(availabilitytext.equals("Busy"))
			    	{
			    		availabilityvalue = 0;
			    	}
			    	else
			    	{
			    		availabilityvalue = 1;
			    	}
			    	
			    	if(visibilitytext.equals("Phone"))
			    	{
			    		visibilityvalue = 1;
			    	}
			    	else
			    	{
			    		visibilityvalue = 0;
			    	}
			    	
					
					StringBuilder dateWithTime = new StringBuilder().append(mSelYear).append("-")
		              .append(mSelMonth+1).append("-")
		              .append(mSelDay).append(" ").append(pad(mSelHour)).append(":")
		              .append(pad(mSelMinute));
					
					updateEventInCalender(dateWithTime);
			    	if(allday.isChecked())
			    	{
			    		alldayvalue = 1;
			    	}
			    	if(hasalarm.isChecked())
			    	{
			    		alarmvalue = 1;
			    	}
			    	
					if(alarmvalue == 1)
					{
						Calendar cal = Calendar.getInstance();       //for using this you need to import java.util.Calendar;
		    			 
						// add minutes to the calendar object
						cal.set(Calendar.MONTH, mSelMonth);
						cal.set(Calendar.YEAR, mSelYear);				
						cal.set(Calendar.DAY_OF_MONTH, mSelDay);
						cal.set(Calendar.HOUR_OF_DAY, mSelHour);
						cal.set(Calendar.MINUTE, mSelMinute);
						//cal.add(Calendar.MINUTE, 5);
						
						Intent alarmintent = new Intent(getApplicationContext(), AlarmReceiver.class);
						alarmintent.putExtra("titletext",title.getText().toString());
						alarmintent.putExtra("notetext",description.getText().toString());
						PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), HELLO_ID, alarmintent,PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);
						AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
						am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
					}
					
					
					Intent intent;// = getIntent();
					intent = new Intent(eventmanager.this, calpage.class);
					startActivity(intent);
					finish();
				}
			});
			 btn_cancel.setOnClickListener(new View.OnClickListener() 
			 {
					
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						if(alterflag == 1)
						{
							alterflag = 0;
						}
						Intent intent;// = getIntent();
						intent = new Intent(eventmanager.this, calpage.class);
						startActivity(intent);
						finish();
					
					}
				});
		 }

	  private void setTime()
	  {

	        final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);
	        mMonth = c.get(Calendar.MONTH);
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	        mHour = c.get(Calendar.HOUR_OF_DAY);
	        mMinute = c.get(Calendar.MINUTE);
	 }

	  @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case TIME_DIALOG_ID:
	                return new TimePickerDialog(this,
	                        mTimeSetListener, mHour, mMinute, false);
	            case DATE_DIALOG_ID:
	                return new DatePickerDialog(this,
	                            mDateSetListener,
	                            mYear, mMonth, mDay);
	        }
	        return null;
	    }
	  private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() 
	  {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth)
            {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay(DATE_DIALOG_ID);
            }
        };

      private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() 
      {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
            {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay(TIME_DIALOG_ID);
            }
        };
 
     private void updateDisplay(int id){
  	  switch (id) {
        case TIME_DIALOG_ID:
      	  if(bBtnCurrentTime){
      	  btn_current_time.setText( new StringBuilder()
            // Month is 0 based so add 1
            .append(pad(mHour)).append(":")
            .append(pad(mMinute))
           );
      	  bBtnCurrentTime=false;
      	  }else{
      		  btn_end_time.setText( new StringBuilder()
                // Month is 0 based so add 1
                .append(pad(mHour)).append(":")
                .append(pad(mMinute))
               );
      	  }
      	  mSelHour=mHour;
      	  mSelMinute=mMinute;
           break;
        case DATE_DIALOG_ID:
      	  if(bBtnCurrentDate){
      	  btn_current_date.setText( new StringBuilder()
            // Month is 0 based so add 1
            .append(mMonth + 1).append("-")
            .append(mDay).append("-")
            .append(mYear).append(" ")
           );
      	  bBtnCurrentDate=false;
      	  }else{
      		  btn_end_date.setText( new StringBuilder()
                // Month is 0 based so add 1
                .append(mMonth + 1).append("-")
                .append(mDay).append("-")
                .append(mYear).append(" ")
               );
      	  }
      	  	mSelYear =mYear ;
			mSelMonth = mMonth;
			mSelDay = mDay;
      	  break;
          
    }
  }
  private static String pad(int c)
  {
      if (c >= 10)
          return String.valueOf(c);
      else
          return "0" + String.valueOf(c);
  }
 private void updateEventInCalender(CharSequence date)
 {
  	
  	ContentValues event = new ContentValues();
  	 Uri eventsUri = Uri.parse("content://com.android.calendar/events"/*getCalendarUriBase(this) +"events"*/);
  	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	         Date dateStart =null;
	  		try {
	  			dateStart = formatter.parse(date.toString());
	  		} catch (ParseException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	    	event.put("calendar_id", cal_id);
	    	event.put("title",title.getText().toString() );
	    	event.put("description",description.getText().toString());
	    	event.put("eventLocation", location.getText().toString());
	    	event.put("dtstart",dateStart.getTime());
	    	event.put("dtend", dateStart.getTime()+HALF_HOUR);
	    	event.put("allDay", alldayvalue);   // 0 for false, 1 for true
	    	event.put("hasAlarm",alarmvalue); // 0 for false, 1 for true
	    	event.put("visibility", visibilityvalue);
	    	event.put("eventStatus", availabilityvalue);
	    	
  	       Uri url = getContentResolver().insert(eventsUri, event);
 } 
 


private String getCalendarUriBase(Activity act) {

	    String calendarUriBase = null;
	    Uri calendars = Uri.parse("content://calendar/calendars");
	    Cursor managedCursor = null;
	    try {
	        managedCursor = act.managedQuery(calendars, null, null, null, null);
	    } catch (Exception e) {
	    }
	    if (managedCursor != null) {
	        calendarUriBase = "content://calendar/";
	    } else {
	        calendars = Uri.parse("content://com.android.calendar/calendars");
	        try {
	            managedCursor = act.managedQuery(calendars, null, null, null, null);
	        } catch (Exception e) {
	        }
	        if (managedCursor != null) {
	            calendarUriBase = "content://com.android.calendar/";
	        }
	    }
	    return calendarUriBase;
	}

public String setStartDate(int day) {
	
	String strDate = String.valueOf(currentYear ) + "-"
			+ String.valueOf(currentMonth + 1) + "-"
			+ String.valueOf(day);
	return strDate;
}

	  

}

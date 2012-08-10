package com.example.android.BusinessCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.android.BusinessCalendar.R;
import com.example.android.BusinessCalendar.calendars;
import com.example.android.BusinessCalendar.calpage;
import com.example.android.Adapter.ImageAdapter;
import com.example.android.Adapter.SeparatedListAdapter;
import com.example.android.Adapter.SeparatedListAdapter1;
import com.example.android.Adapter.barViewAdapter;

public class calpage extends Activity
{
	
	 String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	 String title,location,description;
	 String dayofweek = "Sunday";
	 
	 Integer [] first = new Integer[20];
     Integer [] monthds = new Integer[42];
    
     TextView tmonth;
     TextView sun,mon,tue,wed,thr,fri,sat;
	
     static int month;
     static int offSet; 
	 static int year;
	 static int pos;
	 static int dt;
	
	 int nod,os,temp = 10,firstday = 1,colorvalue = 1,weeknum = 0,color,clrcalendar, CurrentYear,CurrentMonth,gvalue = 0;	
	 
	 Button optioncancelbtn,helpbtn,helpokbtn,viewbtn,google;
	 
	 Boolean viewflag;
	 
	 Dialog dialog,helpdialog;
	
	 Menu menu;
	 
	 ArrayList<EventDetails> events = new ArrayList<EventDetails>();
	
	 Long startdate,enddate;

	 ListView lv;
	 
	 Context mContext,c;
	 
	 private static final int MY_DATE_DIALOG_ID = 3;private static final int SWIPE_MIN_DISTANCE = 120;
	 private static final int SWIPE_MAX_OFF_PATH = 250;
	 private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	 private GestureDetector gestureDetector;
		
	 private Animation slideLeftIn;
	 private Animation slideLeftOut;
	 private Animation slideRightIn;
	 private Animation slideRightOut;
	 private ViewFlipper viewFlipper;
	    
	 View.OnTouchListener gestureListener;
	  
	 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendarpage);
		
		google = (Button) findViewById(R.id.Ongoogle_btn);
		lv = (ListView)findViewById(R.id.callist);
			
		sun = (TextView) findViewById(R.id.sunday_text_view);
		mon = (TextView) findViewById(R.id.monday_text_view);
		tue = (TextView) findViewById(R.id.tuesday_text_view);
		wed = (TextView) findViewById(R.id.wednesday_text_view);
		thr = (TextView) findViewById(R.id.thursday_text_view);
		fri = (TextView) findViewById(R.id.friday_text_view);
		sat = (TextView) findViewById(R.id.saturday_text_view);	
		
		Intent intent = getIntent();
		if(intent.getExtras()!= null)
		{
				dayofweek=intent.getExtras().getString("dayweek");
				color=intent.getExtras().getInt("colorday");
				clrcalendar = intent.getExtras().getInt("calendarcolor");
		}
		
		setCalendarColor(clrcalendar);
	
		if(dayofweek.equals("Sunday"))
		{
			firstday = 1;
		}
		else
		{
			firstday = 2;
			sun.setText("Mon");
			mon.setText("Tue");
			tue.setText("Wed");
			wed.setText("Thr");
			thr.setText("Fri");
			fri.setText("Sat");
			sat.setText("Sun");
		}
	
		mContext=this;
		initCalendarDB();
		viewflag = true;
		
		c=this;
		Calendar mCalendar = Calendar.getInstance();
		month =mCalendar.get(Calendar.MONTH);
		year = mCalendar.get(Calendar.YEAR);
		tmonth = (TextView) findViewById(R.id.month_text_view);
		tmonth.setText(""+Months[month]+","+year);
		tmonth.setGravity(17);
		
		final MonthDisplayHelper mdh = new MonthDisplayHelper(year,month,firstday);
		os=mdh.getOffset();
		nod=mdh.getNumberOfDaysInMonth();
		CurrentYear=mdh.getYear();
	 	CurrentMonth=mdh.getMonth();	
		
		for(int i=0;i<6;i++)
		 {
			 first=mdh.getDigitsForRow(i);
			 System.arraycopy(first,0,monthds,(i*7),first.length);		
		 }
	
		getGridView();
      
			
			viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
	        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
	        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
	        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
	        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
	        
	     
	        gestureDetector = new GestureDetector(new MyGestureDetector());
	        gestureListener = new View.OnTouchListener()
	        {
	            public boolean onTouch(View v, MotionEvent event) 
	            {
	                if (gestureDetector.onTouchEvent(event)) 
	                {
	                    return true;
	                }
	                return false;
	            }
	        };	
	        GridView gridview = (GridView) findViewById(R.id.gridview);
	        gridview.setOnTouchListener(gestureListener);
	        
	        
	        helpbtn = (Button) findViewById(R.id.help_btn);
			 helpbtn.setOnClickListener(new OnClickListener() 
	         {
	 			@Override
	 			public void onClick(View v) 
	 			{
	 					helpdialog = new Dialog(calpage.this);
	 					helpdialog.setContentView(R.layout.mainhelplayout);
	 					helpdialog.setTitle("Help");
	 					helpdialog.setCancelable(true);
	 					helpdialog.show(); 
			
	 					helpokbtn = (Button) helpdialog.findViewById(R.id.help_ok_btn);
	 					helpokbtn.setOnClickListener(new OnClickListener() 
	 					{
	 						@Override
	 						public void onClick(View v) 
	 						{
	 							helpdialog.dismiss();
	 						}
	 					});
	 			}
	         }); 
			 
			
			 google.setOnClickListener(new OnClickListener()
			 {

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub\
					
						
					 	if(gvalue == 0)
					 	{
					 		 ArrayList<EventDetails> events = new ArrayList<EventDetails>();
					 		gvalue = 1;
					 		google.setText("Phone");
						
					 		ContentResolver cr = mContext.getContentResolver();
					 		Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/events"), new String[]{ "calendar_id", "title", "description", "dtstart", "dtend","visibility", "eventLocation","allDay","hasAlarm","eventStatus"}, null, null,"dtstart DESC");         
						 
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
						    		events.add(eventData);
						    		cursor.moveToNext();	
						    
						    }
						    cursor.close();
						    eventDataArr = events;   
					 	}
					 	else
					 	{
					 			int cal_id = 1;
					 		 	ArrayList<EventDetails> events1 = new ArrayList<EventDetails>();
						 		gvalue = 0;
						 		google.setText("Google");
						 		
						 		ContentResolver cr = mContext.getContentResolver();
						 		Cursor cursor = cr.query(Uri.parse("content://com.android.calendar/events"), new String[]{ "calendar_id", "title", "description", "dtstart", "dtend","visibility", "eventLocation","allDay","hasAlarm","eventStatus"}, "calendar_id="+ cal_id, null,"dtstart DESC");         
							 
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
							    		events1.add(eventData);
							    		cursor.moveToNext();	
							    
							    }
							    cursor.close();
							    eventDataArr = events1;  	
					 	}
					 	getGridView();
				}
				 
			 });
		
			 viewbtn = (Button) findViewById(R.id.view_btn);
			 viewbtn.setOnClickListener(new OnClickListener()
			 {
				@Override
				public void onClick(View v) 
				{	
					// TODO Auto-generated method stub
					if(viewflag == true)
					{	
						viewbtn.setBackgroundResource(R.drawable.tblue);
						viewflag = false;
					}
					else if(viewflag == false)
					{
						viewbtn.setBackgroundResource(R.drawable.bars);
						viewflag = true;
					}
					getGridView();		
				}
			 });
	}
	
	 class MyGestureDetector extends SimpleOnGestureListener 
	 {
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                	viewFlipper.setInAnimation(slideLeftIn);
	                    viewFlipper.setOutAnimation(slideLeftOut);

	                	int mn;
						int yy;
						yy=year;
						if(month==11)
						{
							mn=0;
							yy=year+1;
							
						}
						else
						{
							mn=month+1;
						}
						
						CurrentMonth=mn;
						CurrentYear=yy;
						
						 MonthDisplayHelper mdh = new MonthDisplayHelper(year,month,firstday);
						 mdh.nextMonth();
						 
						 offSet=mdh.getOffset();
						 os=offSet;
						 nod=mdh.getNumberOfDaysInMonth();
						 
						 tmonth.setText(""+Months[mn]+","+yy);
						  tmonth.setGravity(17);
						
							 for(int i=0;i<6;i++)
							 {
								 first=mdh.getDigitsForRow(i);
								 System.arraycopy(first,0,monthds,(i*7),first.length);
									
							 }		
					//		viewFlipper.showNext();
							  getGridView();
								month=mn;
								year=yy;
							
								
	                
	                } 
	                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
	                {
	                	viewFlipper.setInAnimation(slideRightIn);
	                    viewFlipper.setOutAnimation(slideRightOut);
	                    
	                    int mn;
						int yy;
						yy=year;
						if(month==0)
						{
							mn=11;
							yy=year-1;
						}
						else
						{
							mn= month-1;
						}
						
						CurrentMonth=mn;
						CurrentYear=yy;
					
						
						 MonthDisplayHelper mdh = new MonthDisplayHelper(year,month,firstday);
						 mdh.previousMonth();
						 offSet=mdh.getOffset();
						 os = offSet;
						 nod=mdh.getNumberOfDaysInMonth();
						 
						 tmonth.setText(""+Months[mn]+","+yy);
						 tmonth.setGravity(17);
						
						 for(int i=0;i<6;i++)
						 {
								 first=mdh.getDigitsForRow(i);
								 System.arraycopy(first,0,monthds,(i*7),first.length);
									
						 }		
					//	 viewFlipper.showPrevious();
						 getGridView();
								month=mn;
								year=yy;	
							
								
	                }
	            } 
	            catch (Exception e) 
	            {
	                // nothing
	            }
	            return false;
	        }
	    }
	 
	    
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        if (gestureDetector.onTouchEvent(event))
		        return true;
		    else
		    	return false;
	    }
	    
	    
 	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	    	super.onCreateOptionsMenu(menu);
	    	MenuInflater newmenu = getMenuInflater();
	    	newmenu.inflate(R.menu.main_menu, menu);
	    	this.menu = menu;
	    	return true;
	    	
	    }
 	    
	    
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	    	switch(item.getItemId())
	    	{		
	    		case R.id.agenda:	
	    			startActivity(new Intent(calpage.this,getAgenda.class));
	    			return true;
	    						    		     
	    		case R.id.search:
	    		{
	    			ArrayList<String> eD = new ArrayList<String>();
	    			for(int i=0;i<eventDataArr.size();i++)
	    		 	   {
	    		 			   eD.add(eventDataArr.get(i).getTitle());
	    		 	   }
	    			
	    			Intent intent = new Intent(calpage.this , searchActivity.class);
					 intent.putExtra("array",eD);
	    			startActivity(intent);	
	    			finish();
	    			return true;	
	    		}	
	    			
	    		case R.id.today:
	    		
	    			Calendar nCalendar = Calendar.getInstance();
	    			month =nCalendar.get(Calendar.MONTH);
	    			year = nCalendar.get(Calendar.YEAR);
	    			tmonth = (TextView) findViewById (R.id.month_text_view);
	    			tmonth.setText(""+Months[month]+","+year);
	    			tmonth.setGravity(17);
	    			final MonthDisplayHelper mdh = new MonthDisplayHelper(year,month,firstday);
	    			os=mdh.getOffset();
	    			nod=mdh.getNumberOfDaysInMonth();
	    			CurrentYear=mdh.getYear();
	    		 	CurrentMonth=mdh.getMonth();
	    			
	    			for(int i=0;i<6;i++)
	    			 {
	    				 first=mdh.getDigitsForRow(i);
	    				 System.arraycopy(first,0,monthds,(i*7),first.length);		
	    			 }
	    			getGridView();
	    			return true;
	    			
	    		case R.id.gotod:
	    			showDialog(MY_DATE_DIALOG_ID);
	    			return true;	
	    			
	    		case R.id.calendars:
	    			
	    			startActivity(new Intent(calpage.this,calendars.class));
	    			return true;
	    			
	    			
	    		case R.id.help :
	    			
	    			helpdialog = new Dialog(calpage.this);
 					helpdialog.setContentView(R.layout.mainhelplayout);
 					helpdialog.setTitle("Help");
 					helpdialog.setCancelable(true);
 					helpdialog.show(); 
		
 					helpokbtn = (Button) helpdialog.findViewById(R.id.help_ok_btn);
 					helpokbtn.setOnClickListener(new OnClickListener() 
 					{
 						@Override
 						public void onClick(View v) 
 						{
 							helpdialog.dismiss();
 						}
 					});
 					return true;
 					
	    		case R.id.settings:
	    			
	    			Intent setintent = new Intent(calpage.this , settingActivity.class);
	    			startActivity(setintent);	
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
	    

	    protected Dialog onCreateDialog(int id)
	    {
	        switch (id)
	        {
	        case MY_DATE_DIALOG_ID:
	            DatePickerDialog dateDlg = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() 
	            {
	         
	            	public void onDateSet(DatePicker view, int tyear, int monthOfYear, int dayOfMonth)
	            	{
	            		  Time chosenDate = new Time();
                          chosenDate.set(dayOfMonth, monthOfYear, tyear);
                          long dtDob = chosenDate.toMillis(true);
                          CharSequence strDate = DateFormat.format("MMMM dd,yyyy", dtDob);
                          Toast.makeText(calpage.this,
                               "Date picked: " + strDate, Toast.LENGTH_SHORT).show();  Toast.makeText(calpage.this,"Date picked: " + strDate, Toast.LENGTH_SHORT).show();
	            				  month = monthOfYear;
	            				  year = tyear;
	            				  tmonth = (TextView) findViewById(R.id.month_text_view);
	            				  tmonth.setText(""+Months[month]+","+year);
	            				  tmonth.setGravity(17);
	            				                    		
	            				                    		final MonthDisplayHelper mdh = new 

	            			MonthDisplayHelper(year,month,firstday);
	            				                    		os=mdh.getOffset();
	            				                    		nod=mdh.getNumberOfDaysInMonth();
	            				                    		CurrentYear=mdh.getYear();
	            				                    	 	CurrentMonth=mdh.getMonth();
	            				                    		
	            				                    		for(int i=0;i<6;i++)
	            				                    		 {
	            				                    			 first=mdh.getDigitsForRow(i);
	            				                    			 System.arraycopy(first,0,monthds,

	            			(i*7),first.length);		
	            				                    		 }
	            				                    		getGridView();
	            				                            
	            				                }}, 2011,0, 1);
	            				              return dateDlg;
	            				        }
	            				        return null;
	            				    }
	    
	    public void getGridView()
	    {
	    	lv.setVisibility(View.INVISIBLE);
	    		if(viewflag == true)
	    		{
	    			GridView gridview = (GridView) findViewById(R.id.gridview);
	    			gridview.setAdapter(new ImageAdapter(c,monthds,os,nod,eventDataArr,CurrentYear,CurrentMonth,color));
	    			gridview.setOnItemLongClickListener(new OnItemLongClickListener()
	    			{
	    				@Override
	    				public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) 
	    				{
	    					// TODO Auto-generated method stub
	    					pos=position;
	    					dt=monthds[pos];
	  			
	    					Intent intent = new Intent(calpage.this,eventmanager.class);
	    					intent.putExtra("date",monthds[position] );
	    					intent.putExtra("month",CurrentMonth);
	    					intent.putExtra("year",CurrentYear);
	    					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	    					startActivity(intent);
	    					return false;	
	    				}
	    			});
	  			
	    			gridview.setOnItemClickListener(new OnItemClickListener()
	    			{
	    				@Override
	    				public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) 
	    				{
	    					// TODO Auto-generated method stub
	    					final int pos = position;
	    					ArrayList<String> eventDataCell = new ArrayList<String>();
	    					ArrayAdapter<String> listadapter;
	    					
	    					
	    					final SeparatedListAdapter adapter;
	    					adapter = new SeparatedListAdapter(calpage.this);
	    					 
	    					 eventDataCell =checkEventOnDate(setStartDate(monthds[pos]),eventDataArr);
	    		        	 listadapter = new ArrayAdapter<String> (calpage.this,R.layout.list_item_n,eventDataCell);
	    		             adapter.addSection(setStartDate(monthds[pos]), listadapter);
	    	               
	    		             lv.setAdapter(adapter);
	    					 lv.setVisibility(View.VISIBLE);
	    					 lv.setPadding(20, 50, 0, 0);
	    					
	    					if(eventDataCell.size() == 0)
	    					{
	    						lv.setVisibility(View.INVISIBLE);
	    					}

	    				    events = checkEventOnDates(setStartDate(monthds[pos]),eventDataArr); 
	    				    // Listen for Click events
	    			          
	   					
	    					lv.setOnItemClickListener(new OnItemClickListener ()
	    					{
	    						@Override
	    						public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
	    						{	
	    							title=events.get(position-1).getTitle();
	    							location=events.get(position-1).getLocation();
	    							description=events.get(position-1).getDescription();
	    							startdate=events.get(position-1).getStartDate();
	    							enddate=events.get(position-1).getEndDate();
	    							

	    							Intent intent = new Intent(calpage.this , editEvents.class);
	    							 intent.putExtra("title",title);
	    						        intent.putExtra("location",location);
	    						        intent.putExtra("description",description);
	    						        intent.putExtra("year", year);
	    						    	intent.putExtra("month",month);
	    						    	intent.putExtra("date",monthds[pos]);
	    							startActivity(intent);
	    							
	    						
	    						}
	    						
	    					});
	    					
	    				}
	  				
	    			});
	        }
	    	else
	    	{
	    		GridView gridview = (GridView) findViewById(R.id.gridview);
    			gridview.setAdapter(new barViewAdapter(c,monthds,os,nod,eventDataArr,CurrentYear,CurrentMonth,color));
    			gridview.setOnItemLongClickListener(new OnItemLongClickListener()
    			{
    				@Override
    				public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) 
    				{
    					// TODO Auto-generated method stub
    					pos=position;
    					dt=monthds[pos];
  			
    					Intent intent = new Intent(calpage.this,eventmanager.class);
    					intent.putExtra("date",monthds[position] );
    					intent.putExtra("month",CurrentMonth);
    					intent.putExtra("year",CurrentYear);
    					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    					startActivity(intent);
    					return false;	
    				}
    			});
  			
    			gridview.setOnItemClickListener(new OnItemClickListener()
    			{
    				@Override
    				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) 
    				{
    					// TODO Auto-generated method stub
    					final int pos = position;
    					ArrayList<String> eventDataCell = new ArrayList<String>();
    					ArrayAdapter<String> listadapter;
    					
    					
    					final SeparatedListAdapter adapter;
    					adapter = new SeparatedListAdapter(calpage.this);
    					 
    					 eventDataCell =checkEventOnDate(setStartDate(monthds[pos]),eventDataArr);
    		        	 listadapter = new ArrayAdapter<String> (calpage.this,R.layout.list_item_n,eventDataCell);
    		             adapter.addSection(setStartDate(monthds[pos]), listadapter);
    	               
    		             lv.setAdapter(adapter);
    					 lv.setVisibility(View.VISIBLE);
    					 lv.setPadding(20, 50, 0, 0);
    					
    					if(eventDataCell.size() == 0)
    					{
    						lv.setVisibility(View.INVISIBLE);
    					}

    				    events = checkEventOnDates(setStartDate(monthds[pos]),eventDataArr); 
    				    // Listen for Click events
    			          
   					
    					lv.setOnItemClickListener(new OnItemClickListener ()
    					{
    						@Override
    						public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
    						{	
    							title=events.get(position-1).getTitle();
    							location=events.get(position-1).getLocation();
    							description=events.get(position-1).getDescription();
    							startdate=events.get(position-1).getStartDate();
    							enddate=events.get(position-1).getEndDate();
    							

    							Intent intent = new Intent(calpage.this , editEvents.class);
    							 intent.putExtra("title",title);
    						        intent.putExtra("location",location);
    						        intent.putExtra("description",description);
    						        intent.putExtra("year", year);
    						    	intent.putExtra("month",month);
    						    	intent.putExtra("date",monthds[pos]);
    							startActivity(intent);
    							
    						
    						}
    						
    					});
    					
    				}
  				
    			});
	    		
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
	    
	    
	    private  ArrayList<EventDetails> checkEventOnDates(String cellDate,ArrayList<EventDetails> eventDataArray)
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
		
		
		public void setCalendarColor(int co)
		   {
			  
			   
			   if(co == 0)
			   {
				   google.setBackgroundColor(Color.parseColor("#151B8D"));
			   }
			   else if(co == 1)
			   {
				   google.setBackgroundColor(Color.parseColor("#4AA02C"));
			   }
			   else if(co == 2)
			   {
				   google.setBackgroundColor(Color.parseColor("#808080"));
			   }
			   else if(co == 3)
			   {
				   google.setBackgroundColor(Color.parseColor("#810541"));
			   }
			   else if(co == 4)
			   {
				   google.setBackgroundColor(Color.parseColor("#C12267"));
			   }
			   else if(co == 5)
			   {
				   google.setBackgroundColor(Color.parseColor("#7D1B7E"));
			   }
			   else if(co == 6)
			   {
				   google.setBackgroundColor(Color.parseColor("#3EA99F"));
			   }
			   
		   }
		   
}
		

		

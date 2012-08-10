package com.example.android.BusinessCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class settingActivity extends Activity
{
	Spinner firstday,colortoday,colorcalendar;
	Button save,cancel; 
	String dayofweek;
	int color,ccalendar;
	 CheckBox weeknum;
	 int weekvalue;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		
		firstday = (Spinner) findViewById(R.id.spinner_dayofweek);
		colortoday = (Spinner) findViewById(R.id.spinner_colortoday);
		colorcalendar = (Spinner) findViewById(R.id.spinner_color_calendar);
		save = (Button) findViewById(R.id.btn_settingSave);
		cancel = (Button) findViewById(R.id.btn_settingCancel);
		weeknum = (CheckBox) findViewById(R.id.check_weeknum);
	
		dayofweek = firstday.getSelectedItem().toString();
		
		save.setOnClickListener(new OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{	
				dayofweek = firstday.getSelectedItem().toString();
				color = colortoday.getSelectedItemPosition();
				ccalendar = colorcalendar.getSelectedItemPosition();
				
				Intent intent = new Intent(settingActivity.this , calpage.class);
				 intent.putExtra("dayweek",dayofweek);
				 intent.putExtra("colorday", color);
				 intent.putExtra("calendarcolor", ccalendar);
				startActivity(intent);	
    			finish();
			}	 
		 });
		
		
	}
	

}

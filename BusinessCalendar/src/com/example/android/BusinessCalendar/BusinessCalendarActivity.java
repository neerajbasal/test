package com.example.android.BusinessCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BusinessCalendarActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   
       
        TextView tv = (TextView) findViewById(R.id.TextView02);
        tv.setText( Html.fromHtml("\nIf you have additional question on using Business calendar or if you encounter any errors please visit the support page of our "+ "<a href=\"http://www.google.com\">website</a> "));
        tv.setMovementMethod(LinkMovementMethod.getInstance());  
        
        Button bletsgo = (Button) findViewById(R.id.lets_go);
        bletsgo.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			//startActivity(new Intent("com.example.android.BusinessCalendar1.sintent"));
			Intent intent = new Intent(BusinessCalendarActivity.this, calpage.class);
			startActivity(intent);
			finish();
			
		}
	});
		
    }
    
    
   
}
package com.example.android.BusinessCalendar;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class startPage extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpage);
		
		Button bsave = (Button) findViewById(R.id.selection_save_btn);
	    bsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub	
				Intent intent = new Intent(startPage.this,calpage.class);
				startActivity(intent);
				finish();
				
			}
		});
			
		
		
	}
	
	

}


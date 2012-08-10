package com.android.animation.lib;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.animation.lib.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
     
//       LinearLayout linearRight =(LinearLayout)findViewById(R.id.SliderContainerRight);
//     ImageView iv = new ImageView(this);
//     iv.setImageResource(R.drawable.)
//       sHelper.setListInSlider(linearRight, arrayList1,false,imageV);
      
       
    }
   public void setHeader(){
	   SliderHelper sHelper = new SliderHelper(MainActivity.this);
       sHelper.setIconsForLeftContainer(R.drawable.kalender_out, R.drawable.kalender_in);
       sHelper.setIconsForRightContainer(R.drawable.time_out, R.drawable.time_in);
       ArrayList<Object> arrayList = new ArrayList<Object>();
       ArrayList<Object> arrayList1 = new ArrayList<Object>();
       arrayList.add(R.drawable.one);
       arrayList.add(R.drawable.two);
       arrayList.add(R.drawable.three);
       arrayList.add(R.drawable.four);
       arrayList.add(R.drawable.five);
       arrayList.add(R.drawable.six);
       arrayList.add(R.drawable.seven);
       arrayList.add(R.drawable.eight);
       arrayList.add(R.drawable.six);
       for(int i=0;i<8;i++){
       arrayList1.add("Neeraj" +i+"Basal");
       }
       LinearLayout ll = (LinearLayout)findViewById(R.id.main);
     
        
       ll.addView(sHelper.getView());
//       LinearLayout linearL =(LinearLayout)findViewById(R.id.SliderContainerLeft);
//       ImageView imageV = (ImageView)findViewById(R.id.imageview);
//       ImageView iv = new ImageView(this);
//       iv.setImageResource(R.drawable.)
       sHelper.setListInSlider( arrayList,true);
   }
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		System.err.println("");
//	}
     
  
}
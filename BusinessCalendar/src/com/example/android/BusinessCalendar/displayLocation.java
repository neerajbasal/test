package com.example.android.BusinessCalendar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class displayLocation extends MapActivity
{
	MapView mapView;
	String location;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapdisplay);
		
		 Intent intent = getIntent();
			if(intent.getExtras()!= null)
			{
				location=intent.getExtras().getString("location");
			}
			
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		List mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);
        CustomItemizedOverlay itemizedOverlay = 
             new CustomItemizedOverlay(drawable, this);
        
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());  
        GeoPoint point;
        try {
            List<Address> addresses = geoCoder.getFromLocationName(location, 5);
            String add = "";
            if (addresses.size() > 0) {
                point = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6), 
                        (int) (addresses.get(0).getLongitude() * 1E6));
                OverlayItem overlayitem = 
                        new OverlayItem(point, "Hello", "I m at" + point);
                   
                itemizedOverlay.addOverlay(overlayitem);
                mapOverlays.add(itemizedOverlay);      
                MapController mapController = mapView.getController();
                mapController.animateTo(point);
                mapController.setZoom(6);
                
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
       
		
		
		}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

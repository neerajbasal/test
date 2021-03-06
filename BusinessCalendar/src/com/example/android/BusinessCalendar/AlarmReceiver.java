package com.example.android.BusinessCalendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
 
public class AlarmReceiver extends BroadcastReceiver 
{
 
	private static int NOTIFICATION_ID = 1;
 
	@Override
	public void onReceive(Context context, Intent intent)
	{
 
		NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.calendar, "Combi Note",System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, new Intent(context, AlarmReceiver.class), 0);
		Bundle extras=intent.getExtras();
		String title=extras.getString("titletext");
		String note=extras.getString("notetext");
		notification.setLatestEventInfo(context, note, title, contentIntent);
		notification.flags = Notification.FLAG_INSISTENT;
		notification.defaults |= Notification.DEFAULT_SOUND;
		manger.notify(NOTIFICATION_ID++, notification);

	}
 
};


package com.example.android.Adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.android.BusinessCalendar.EventDetails;
import com.example.android.BusinessCalendar.R;

public class barViewAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] mThumbIds;
	private int offset;
	private int noofdays;
	static Integer[] num;
	ArrayList<EventDetails> eventData;
	int currentYear, currentMonth, color;
	int month, year;

	public barViewAdapter(Context c, Integer[] first, int offst, int nofdays, ArrayList<EventDetails> eventData, int year, int month, int color) {
		mContext = c;
		mThumbIds = first.clone();
		offset = offst;
		noofdays = nofdays;
		num = first.clone();
		this.eventData = eventData;
		currentYear = year;
		currentMonth = month;
		this.month = month;
		this.year = year;
		this.color = color;

	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ArrayList<EventDetails> eventDataCell = new ArrayList<EventDetails>();
		ViewHolder holder = null;
		LinearLayout myLayout = null;
		Calendar mCalendar = Calendar.getInstance();
		int month = mCalendar.get(Calendar.MONTH);
		int year = mCalendar.get(Calendar.YEAR);
		int date = mCalendar.get(Calendar.DAY_OF_MONTH);
		@SuppressWarnings("unused")
		int weekno = mCalendar.get(Calendar.WEEK_OF_YEAR);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grid_event_list, null);
			myLayout = (LinearLayout) convertView.findViewById(R.id.gridEvents);
			holder = new ViewHolder();
			holder.heading = (TextView) convertView.findViewById(R.id.dates);

			convertView.setLayoutParams(new GridView.LayoutParams(40, 50));
			convertView.setBackgroundColor(Color.WHITE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < offset || position >= (offset + noofdays)) {
			holder.heading.setTextColor(Color.GRAY);
			holder.heading.setText("" + mThumbIds[position]);

		}

		else if (mThumbIds[position] == date && month == currentMonth && year == currentYear) {
			holder.heading.setTextColor(Color.parseColor("#151B8D"));
			holder.heading.setText("" + mThumbIds[position]);
			if (color == 0) {
				holder.heading.setBackgroundColor(Color.LTGRAY);
				convertView.setBackgroundColor(Color.LTGRAY);

			} else if (color == 1) {
				holder.heading.setBackgroundColor(Color.parseColor("#E6A9E7"));
				convertView.setBackgroundColor(Color.parseColor("#E6A9E7"));

			} else if (color == 2) {
				holder.heading.setBackgroundColor(Color.parseColor("#FAAFBA"));
				convertView.setBackgroundColor(Color.parseColor("#FAAFBA"));

			} else if (color == 3) {
				holder.heading.setBackgroundColor(Color.parseColor("#6AFB92"));
				convertView.setBackgroundColor(Color.parseColor("#6AFB92"));

			}

			else if (color == 4) {
				holder.heading.setBackgroundColor(Color.parseColor("#AFDCEC"));
				convertView.setBackgroundColor(Color.parseColor("#AFDCEC"));

			}

			else if (color == 5) {
				holder.heading.setBackgroundColor(Color.parseColor("#EE9A4D"));
				convertView.setBackgroundColor(Color.parseColor("#EE9A4D"));

			}

		}

		else {
			holder.heading.setTextColor(Color.BLACK);
			holder.heading.setText("" + mThumbIds[position]);

		}
		if (position >= offset && position < (offset + noofdays)) {

			eventDataCell = checkEventOnDate(setStartDate(mThumbIds[position]), eventData);

			if (eventDataCell.size() > 0) {
				if (eventDataCell.get(0).getStartDateStr().equals(setStartDate(mThumbIds[position]))) {

					for (int i = 0; i < eventDataCell.size(); i++) {
						TextView tv = new TextView(mContext);
						if (eventDataCell.get(i).getAvailability() == 1) {
							tv.setBackgroundResource(R.drawable.greenbar);
						} else if (eventDataCell.get(i).getAvailability() == 0) {
							tv.setBackgroundResource(R.drawable.redbar);
						}
						tv.setTextSize(9f);
						tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						holder.textView.add(tv);
						myLayout.addView(tv);

					}
				}
			}
		}

		return convertView;

	}

	static class ViewHolder {

		TextView heading;
		ArrayList<TextView> textView = new ArrayList<TextView>();

	}

	private ArrayList<EventDetails> checkEventOnDate(String cellDate, ArrayList<EventDetails> eventDataArray) {
		ArrayList<EventDetails> eD = new ArrayList<EventDetails>();
		for (int i = 0; i < eventDataArray.size(); i++) {
			if (cellDate.equalsIgnoreCase(eventDataArray.get(i).getStartDateStr())) {
				eD.add(eventDataArray.get(i));

			}
		}
		return eD;
	}

	public String setStartDate(int day) {

		String strDate = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth + 1) + "-" + String.valueOf(day);
		return strDate;
	}

}

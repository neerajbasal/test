package com.example.android.BusinessCalendar;

import java.sql.Date;

public class EventDetails
{
      String title,description,location,startDateStr;
      Long startDate,endDate,sttime,endtime;
      Boolean dayflag,alarmflag;
      int availability,visibility;
      
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public String getLocation() 
	{
		return location;
	}
	public void setLocation(String location) 
	{
		this.location = location;
	}
	public Long getStartDate() 
	{
		return startDate;
	}
	
	public String getStartDateStr() 
	{
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) 
	{
	 
		this.startDateStr = startDateStr;
	}
	public void setStartDate(Long startDate) 
	{
		Date date = new Date(startDate);
		String strDate = String.valueOf(date.getYear() + 1900) + "-"
				+ String.valueOf(date.getMonth() + 1) + "-"
				+ String.valueOf(date.getDate());
		setStartDateStr(strDate);
		this.startDate = startDate;
	}
	public Long getEndDate() 
	{
		return endDate;
	}
	public void setEndDate(Long endDate) 
	{
		this.endDate = endDate;
	}
	
	public void setStartTime(Long sttime)
	{
		this.sttime = sttime;
	}
	
	public void setEndTime(Long endtime)
	{
		this.endtime = endtime;
	}
	
	public Long getStartTime()
	{
		return sttime;
	}
	
	public Long getEndTime()
	{
		return endtime;
	}
	
	public void setisAllDay(Boolean dayflag)
	{
		this.dayflag = dayflag;
	}
	
	public boolean getisAllDay()
	{
		return dayflag;
	}
	
	public void sethasAlarm(Boolean alarmflag)
	{
		this.alarmflag = alarmflag;
	}
	
	public boolean gethasAlarm()
	{
		return alarmflag;
	}
	
	public void setAvailability(int availability)
	{
		this.availability = availability;
	}
	
	public int getAvailability()
	{
		return availability;
	}
	
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
	public int getVisibility()
	{
		return visibility;
	}
	
}

package com.natepaulus.dailyemail.web.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DeliveryTimeEntryForm {
	
	@NotNull(message = "Time Zone can not be blank.")
	private String timezone;
		
	@NotNull(message = "Please enter time in HH:MM AM or HH:MM PM.")
	@Pattern(regexp = "(1[012]|0[1-9]):[0-5][0-9](\\s)(AM|PM)", message = "Please enter time in HH:MM AM or HH:MM PM.")
	private String weekDayTime;
	
	@NotNull(message = "Please enter time in HH:MM AM or HH:MM PM.")
	@Pattern(regexp = "(1[012]|0[1-9]):[0-5][0-9](\\s)(AM|PM)", message = "Please enter time in HH:MM AM or HH:MM PM.")
	private String weekEndTime;
	
	private boolean weekDayDisabled;
	
	private boolean weekEndDisabled;

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getWeekDayTime() {
		return weekDayTime;
	}

	public void setWeekDayTime(String weekDayTime) {
		this.weekDayTime = weekDayTime;
	}

	public String getWeekEndTime() {
		return weekEndTime;
	}

	public void setWeekEndTime(String weekEndTime) {
		this.weekEndTime = weekEndTime;
	}

	public boolean isWeekDayDisabled() {
		return weekDayDisabled;
	}

	public void setWeekDayDisabled(boolean weekDayDisabled) {
		this.weekDayDisabled = weekDayDisabled;
	}

	public boolean isWeekEndDisabled() {
		return weekEndDisabled;
	}

	public void setWeekEndDisabled(boolean weekEndDisabled) {
		this.weekEndDisabled = weekEndDisabled;
	}

	
	
	
	
	
}

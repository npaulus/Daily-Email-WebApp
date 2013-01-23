package com.natepaulus.dailyemail.web.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The Class DeliveryTimeEntryForm matches the delivery time entry form on the
 * account page. This class validates that the user input for time is valid and
 * in the format HH:MM A
 */
public class DeliveryTimeEntryForm {

	/** The timezone. */
	@NotNull(message = "Time Zone can not be blank.")
	private String timezone;

	/** The week day delivery time. */
	@NotNull(message = "Please enter time in HH:MM AM or HH:MM PM.")
	@Pattern(regexp = "(1[012]|0[1-9]):[0-5][0-9](\\s)(AM|PM)", message = "Please enter time in HH:MM AM or HH:MM PM.")
	private String weekDayTime;

	/** The week end delivery time. */
	@NotNull(message = "Please enter time in HH:MM AM or HH:MM PM.")
	@Pattern(regexp = "(1[012]|0[1-9]):[0-5][0-9](\\s)(AM|PM)", message = "Please enter time in HH:MM AM or HH:MM PM.")
	private String weekEndTime;

	/** The week day disabled or not disabled value. */
	private boolean weekDayDisabled;

	/** The week end disabled or not disabled value. */
	private boolean weekEndDisabled;

	/**
	 * Gets the timezone.
	 * 
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * Sets the timezone.
	 * 
	 * @param timezone
	 *            the new timezone
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * Gets the week day time.
	 * 
	 * @return the week day time
	 */
	public String getWeekDayTime() {
		return weekDayTime;
	}

	/**
	 * Sets the week day time.
	 * 
	 * @param weekDayTime
	 *            the new week day time
	 */
	public void setWeekDayTime(String weekDayTime) {
		this.weekDayTime = weekDayTime;
	}

	/**
	 * Gets the week end time.
	 * 
	 * @return the week end time
	 */
	public String getWeekEndTime() {
		return weekEndTime;
	}

	/**
	 * Sets the week end time.
	 * 
	 * @param weekEndTime
	 *            the new week end time
	 */
	public void setWeekEndTime(String weekEndTime) {
		this.weekEndTime = weekEndTime;
	}

	/**
	 * Checks if is week day disabled.
	 * 
	 * @return true, if is week day disabled
	 */
	public boolean isWeekDayDisabled() {
		return weekDayDisabled;
	}

	/**
	 * Sets the week day disabled.
	 * 
	 * @param weekDayDisabled
	 *            the new week day disabled
	 */
	public void setWeekDayDisabled(boolean weekDayDisabled) {
		this.weekDayDisabled = weekDayDisabled;
	}

	/**
	 * Checks if is week end disabled.
	 * 
	 * @return true, if is week end disabled
	 */
	public boolean isWeekEndDisabled() {
		return weekEndDisabled;
	}

	/**
	 * Sets the week end disabled.
	 * 
	 * @param weekEndDisabled
	 *            the new week end disabled
	 */
	public void setWeekEndDisabled(boolean weekEndDisabled) {
		this.weekEndDisabled = weekEndDisabled;
	}

}

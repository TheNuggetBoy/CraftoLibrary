/*
 * Copyright (C) 2014 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for formatting strings to a specific time.
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 26.10.2014
 * @see <a href="https://github.com/Craftolution">CraftolutionDE on Github</a>
 * @see SimpleDateFormat
 * @see Duration
 */
public class TimeUtils {

	private TimeUtils() { }

	/** A dateformatter to format the given strings. */
	private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);

	/**
	 * @return Returns the current time in the following format: <blockquote>"dd.MM.yyyy HH:mm"</blockquote>
	 * Click <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">here</a> to read all format possibilites. <br> <br>
	 */
	public static String getCurrentTime() {
		return TimeUtils.getCurrentTime("dd.MM.yyyy HH:mm");
	}

	/**
	 * Checks the current time and returns it in the specified format.
	 * @param format - The format in which the time should be returned.
	 * @return Returns the current time in the given format.
	 */
	public static String getCurrentTime(final String format) {
		Check.notNullOld(format, "The format must not be null.");
		TimeUtils.checkFormat(format);
		synchronized (TimeUtils.df) { return TimeUtils.df.format(new Date(System.currentTimeMillis())); }
	}

	/**
	 * Parses the given timestamp to a string in the following format: <blockquote>"dd.MM.yyyy HH:mm"</blockquote>
	 * Click <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">here</a> to read all formatting possibilites. <br> <br>
	 * @param time - The timestamp to parse.
	 * @return Returns the string version of the given timestamp.
	 */
	public static String getTime(final Timestamp time) {
		Check.notNull("The timestamp must not be null.", time);
		return TimeUtils.getTime(time.getTime(), "dd.MM.yyyy HH:mm");
	}

	/**
	 * Parses the given time (in milliseconds) to a {@link String} in the following format: <blockquote>"dd.MM.yyyy HH:mm"</blockquote>
	 * Click <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">here</a> to read all formatting possibilites. <br> <br>
	 * @param time - The time to parse.
	 * @return Returns a string version of the given time.
	 */
	public static String getTime(final long time) {
		return TimeUtils.getTime(time, "dd.MM.yyyy HH:mm");
	}

	/**
	 * Parses the given timestamp into a string with the specified format.
	 * Click <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">here</a> to read all formatting possibilites. <br> <br>
	 * @param time - The timestamp to parse.
	 * @param format - The format.
	 * @return Returns a string version of the given timestamp in the specified format.
	 */
	public static String getTime(final Timestamp time, final String format) {
		Check.notNull("The timestamp/format must not be null.", time, format);
		return TimeUtils.getTime(time.getTime(), format);
	}

	/**
	 * Parses the given time (in milliseconds!) into a string with the specified format.
	 * Click <a href="http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html">here</a> to read all format possibilites. <br> <br>
	 * @param time - The time to parse.
	 * @param format - The format.
	 * @return Returns a string version of the given time in the specified format.
	 */
	public static String getTime(final long time, final String format) {
		Check.notNullOld(format, "The format must not be null.");
		TimeUtils.checkFormat(format);
		synchronized (TimeUtils.df) { return TimeUtils.df.format(new Date(time)); }
	}

	/**
	 * Returns the current time in milliseconds.
	 * @return The difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC.
	 */
	public static long getCurrentMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Checks the current weekday and returns it as a string translated by the specified language.
	 * @param lang - The language in which the weekday should be returned.
	 * @return Returns the current weekday.
	 */
	public static String getWeekday() {
		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
			case 1: return "SUNDAY";
			case 2: return "MONDAY";
			case 3: return "TUESDAY";
			case 4: return "WEDNESDAY";
			case 5: return "THURSDAY";
			case 6: return "FRIDAY";
			case 7: return "SATURDAY";
			default: return "$ERROR$";
		}
	}

	private static void checkFormat(final String format) {
		Check.notNullOld(format, "The format must not be null");
		synchronized (TimeUtils.df) {
			if (!TimeUtils.df.toPattern().equalsIgnoreCase(format)) {
				TimeUtils.df = new SimpleDateFormat(format, Locale.GERMANY);
			}
		}
	}
}
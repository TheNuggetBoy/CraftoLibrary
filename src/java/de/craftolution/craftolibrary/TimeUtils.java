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
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

	private static String currentPattern = "dd.MM.yyyy HH:mm";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeUtils.currentPattern).withZone(ZoneId.systemDefault());

	/** TODO: Documentation */
	public static Instant toInstant(final Timestamp timestamp) { return Instant.ofEpochMilli(timestamp.getTime()); }

	/** TODO: Documentation */
	public static Timestamp toTimestamp(final Instant instant) { return new Timestamp(instant.toEpochMilli()); }

	/** TODO: Documentation */
	public static Timestamp currentTimestamp() { return new Timestamp(System.currentTimeMillis()); }

	/** TODO: Documentation */
	public static String parseCurrentTime() { return TimeUtils.parseCurrentTime("dd.MM.yyyy HH:mm"); }

	/** TODO: Documentation */
	public static String parseCurrentTime(final String format) {
		TimeUtils.checkFormatter(Check.notNull(format, "The format cannot be null!"));
		synchronized (TimeUtils.formatter) { return TimeUtils.formatter.format(Instant.now()); }
	}

	/** TODO: Documentation */
	public static String parseTime(final Instant instant) {
		synchronized (TimeUtils.formatter) { return TimeUtils.formatter.format(Check.notNull(instant, "The instant cannot be null!")); }
	}

	/** TODO: Documentation */
	public static String parseTime(final Timestamp timestamp) {
		synchronized (TimeUtils.formatter) { return TimeUtils.formatter.format(TimeUtils.toInstant( Check.notNull(timestamp, "The timestamp cannot be null!") )); }
	}

	/** TODO: Documentation */
	public static String parseTime(final Instant instant, final String format) {
		TimeUtils.checkFormatter(Check.notNull(format, "The format cannot be null!"));
		synchronized (TimeUtils.formatter) { return TimeUtils.formatter.format( Check.notNull(instant, "The instant cannot be null!") ); }
	}

	/** TODO: Documentation */
	public static String parseTime(final Timestamp timestamp, final String format) {
		TimeUtils.checkFormatter(Check.notNull(format, "The format cannot be null!"));
		synchronized (TimeUtils.formatter) { return TimeUtils.formatter.format(TimeUtils.toInstant( Check.notNull(timestamp, "The timestamp cannot be null!") )); }
	}

	/** TODO: Documentation */
	public static DayOfWeek getCurrentWeekday() { return LocalDate.now().getDayOfWeek(); }

	private static void checkFormatter(final String format) {
		if (!TimeUtils.currentPattern.equals(format)) {
			TimeUtils.currentPattern = format;
			TimeUtils.formatter = DateTimeFormatter.ofPattern(TimeUtils.currentPattern).withZone(ZoneId.systemDefault());
		}
	}

}
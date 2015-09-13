/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.function.Supplier;

/**
 * TODO: File description for Duration.java
 *
 * @author Fear837
 * @version 1.0
 * @since 05.02.2015 16:16:45
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public final class Duration implements Supplier<Long>, com.google.common.base.Supplier<Long>, Serializable, Comparable<Duration>, Cloneable, ToStringable {

	// --- Static stuff ---

	private static final long serialVersionUID = -594172667288022414L;

	// --- Nonstatic ---

	private long duration; // in milliseconds

	private Duration(final long millis) { this.duration = millis; }

	/** @return Returns {@code true} if this duration is {@code 0}. */
	public boolean isZero() { return this.duration == 0; }

	/** @return Returns {@code true} if this duration is positive. */
	public boolean isPositive() { return this.duration > 0; }

	/** @return Returns {@code true} if this duration is negative. */
	public boolean isNegative() { return this.duration < 0; }

	/**
	 * Adds the other duration on this one.
	 * @param other - The other duration.
	 * @return Returns this duration object.
	 */
	public Duration add(final Duration other) {
		this.duration += other.inMillis();
		return this;
	}

	/**
	 * Adds the given milliseconds to this duration.
	 * @param millis - The milliseconds to add.
	 * @return Returns this duration object.
	 */
	public Duration addMillis(final long millis) {
		this.duration += millis;
		return this;
	}

	/**
	 * Adds the given ticks to this duration.
	 * @param ticks - The ticks to add.
	 * @return Returns this duration object.
	 */
	public Duration addTicks(final long ticks) {
		this.duration += ticks * 50;
		return this;
	}

	/**
	 * Adds the given seconds to this duration.
	 * @param seconds - The seconds to add.
	 * @return Returns this duration object.
	 */
	public Duration addSeconds(final long seconds)  {
		this.duration += seconds * 1000;
		return this;
	}

	/**
	 * Adds the given minutes to this duration.
	 * @param minutes - The minutes to add.
	 * @return Returns this duration object.
	 */
	public Duration addMinutes(final int minutes) {
		this.duration += minutes * 60 * 1000;
		return this;
	}

	/**
	 * Adds the given hours to this duration.
	 * @param hours - The hours to add.
	 * @return Returns this duration object.
	 */
	public Duration addHours(final int hours) {
		this.duration += hours * 60 * 60 * 1000;
		return this;
	}

	/**
	 * Adds the given days to this duration.
	 * @param days - The days to add.
	 * @return Returns this duration object.
	 */
	public Duration addDays(final int days) {
		this.duration += days * 24 * 60 * 60 * 1000;
		return this;
	}

	/**
	 * Adds the given weeks to this duration.
	 * @param weeks - The weeks to add.
	 * @return Returns this duration object.
	 */
	public Duration addWeeks(final int weeks) {
		this.duration += weeks * 7 * 24 * 60 * 60 * 1000;
		return this;
	}

	@Override
	public Long get() { return this.duration; }

	/** @return Returns this duration in milliseconds. */
	public long inMillis() { return this.duration; }

	/** @return Returns this duration in ticks. */
	public long inTicks() { return this.duration/50; }

	/** @return Returns this duration in seconds. */
	public long inSeconds() { return this.duration / 1000; }

	/** @return Returns this duration in minutes. */
	public long inMinutes() { return this.duration / 1000 / 60; }

	/** @return Returns this duration in hours. */
	public long inHours() { return this.duration / 1000 / 60 / 60; }

	/** @return Returns this duration in days. */
	public long inDays() { return this.duration / 1000 / 60 / 60 / 24; }

	/** @return Returns this duration in weeks. */
	public long inWeeks() { return this.duration / 1000 / 60 / 60 / 24 / 7; }

	/** @return Returns {@code true} if this duration is longer than the other specified one. */
	public boolean isLongerThan(final Duration other) { return this.duration > other.inMillis(); }

	/** @return Returns {@code true} if this duration is shorter than the other specified one. */
	public boolean isShorterThan(final Duration other) { return this.duration < other.inMillis(); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.duration ^ this.duration >>> 32);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj != null) {
			if (obj != this) {
				if (obj instanceof Duration) {
					final Duration other = (Duration)obj;
					if (other.inMillis() == this.inMillis()) {
						return true;
					}
					else { return false; }
				}
				else { return false; }
			}
			else { return true; }
		}
		else { return false; }
	}

	@Override
	public int compareTo(final Duration o) {
		return this.duration > o.inMillis() ? 1 : this.duration < o.inMillis() ? -1 : 0;
	}

	@Override
	public String toString() {
		return this.buildToString()
				.with("millis", this.duration)
				.toString();
	}

	// --- Static access ---

	/** TODO: Documentation */
	public static final Duration zero() { return new Duration(0); }

	/**
	 * Creates a new {@link Duration} which is the same as the specified one.
	 * @param duration - The duration to copy.
	 * @return Returns a new duration.
	 */
	public static Duration of(final Duration duration) {
		Check.notNull(duration, "The duration cannot be null!");
		return new Duration(duration.inMillis());
	}

	/**
	 * Creates a new {@link Duration} instance by trying to parse the given text.
	 * <p>
	 * For example:
	 * <pre> System.out.println( Duration.of("2w5d12h34m2s18t).inMillis() ); </pre>
	 * Will print the following duration in milliseconds: <code>1686842900</code>
	 * </p>
	 *
	 * @param text - The text to parse.
	 * @return Returns the duration which has been parsed.
	 * @throws IllegalArgumentException If the text contains an unknown time argument.
	 */
	public static Duration of(final String text) throws IllegalArgumentException {
		Check.notNull(text, "The given text cannot be null!");
		Check.notEmpty(text, "The given text cannot be empty!");
		long time = 0;
		final StringBuilder timeBuilder = new StringBuilder();
		for (final char character : text.toCharArray()) {
			if (Check.isInt(character)) {
				timeBuilder.append(character);
			}
			else {
				if (Check.isInt(timeBuilder.toString())) {
					final int previousNumber = Integer.parseInt(timeBuilder.toString());
					switch (character) {
						case 'w': // week
							time += previousNumber * 7 * 24 * 60 * 60 * 1000;
							break;
						case 'd': // day
							time += previousNumber * 24 * 60 * 60 * 1000;
							break;
						case 'h': // hour
							time += previousNumber * 60 * 60 * 1000;
							break;
						case 'm': // minute
							time += previousNumber * 60 * 1000;
							break;
						case 's': // second
							time += previousNumber * 1000;
							break;
						case 't': // tick
							time += previousNumber * 50;
							break;
						default: throw new IllegalArgumentException("Invalid time parsing argument: " + character);
					}
					timeBuilder.delete(0, timeBuilder.length());
				}
				else { throw new IllegalArgumentException("Something went wrong while parsing the given text: '" + timeBuilder.toString()+"'"); }
			}
		}

		return Duration.ofMillis(time);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of milliseconds.
	 * @param millis - The duration in milliseconds.
	 * @return Returns a new duration.
	 */
	public static Duration ofMillis(final long millis) {
		return new Duration(millis);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of ticks.
	 * @param ticks - The duration in ticks.
	 * @return Returns a new duration.
	 */
	public static Duration ofTicks(final long ticks) {
		return Duration.ofMillis(ticks * 50);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of seconds.
	 * @param seconds - The duration in seconds.
	 * @return Returns a new duration.
	 */
	public static Duration ofSeconds(final long seconds)  {
		return Duration.ofMillis(seconds * 1000);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of minutes.
	 * @param minutes - The duration in minutes.
	 * @return Returns a new duration.
	 */
	public static Duration ofMinutes(final int minutes) {
		return Duration.ofMillis(minutes * 60 * 1000);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of hours.
	 * @param hours - The duration in hours.
	 * @return Returns a new duration.
	 */
	public static Duration ofHours(final int hours) {
		return Duration.ofMillis(hours * 60 * 60 * 1000);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of days.
	 * @param days - The duration in days.
	 * @return Returns a new duration.
	 */
	public static Duration ofDays(final int days) {
		return Duration.ofMillis(days * 24 * 60 * 60 * 1000);
	}

	/**
	 * Initializes a new {@link Duration} with the specified amount of weeks.
	 * @param weeks - The duration in weeks.
	 * @return Returns a new duration.
	 */
	public static Duration ofWeeks(final int weeks) {
		return Duration.ofMillis(weeks * 7 * 24 * 60 * 60 * 1000);
	}

	/**
	 * Returns the duration between the two unix points.
	 * @param unixStart - The start.
	 * @param unixEnd - The end.
	 * @return Returns the duration between these two points.
	 */
	public static Duration between(final long unixStart, final long unixEnd) {
		return Duration.ofMillis(unixEnd - unixStart);
	}

	/**
	 * Returns the duration between the two specified timestamps.
	 * @param start - The first timestamp.
	 * @param end - The second timestamp.
	 * @return Returns the duration between these two timestamps.
	 */
	public static Duration between(final Timestamp start, final Timestamp end) {
		Check.notNull(start, "The start cannot be null!");
		Check.notNull(end, "The end timestamp cannot be null!");
		return Duration.ofMillis(end.getTime() - start.getTime());
	}

	/** TODO: Documentation */
	public static Duration sum(final Pair<Duration, Duration> durations) {
		Check.notNull(durations, "The durations pair cannot be null!");
		return Duration.sum(durations.getFirst(), durations.getSecond());
	}

	/** TODO: Documentation */
	public static Duration sum(final Duration first, final Duration second) {
		Check.notNull(first, "The first duration cannot be null!");
		Check.notNull(second, "The second duration cannot be null!");
		return Duration.ofTicks(first.get() + second.get());
	}

	/** TODO: Documentation */
	public static Duration diff(final Pair<Duration, Duration> durations) {
		Check.notNull(durations, "The durations pair cannot be null!");
		return Duration.diff(durations.getFirst(), durations.getSecond());
	}

	/** TODO: Documentation */
	public static Duration diff(final Duration first, final Duration second) {
		Check.notNull(first, "The first duration cannot be null!");
		Check.notNull(second, "The second duration cannot be null!");
		return Duration.ofTicks(first.get() - second.get());
	}

	/** TODO: Documentation */
	public static Duration prod(final Pair<Duration, Duration> durations) {
		Check.notNull(durations, "The durations pair cannot be null!");
		return Duration.prod(durations.getFirst(), durations.getSecond());
	}

	/** TODO: Documentation */
	public static Duration prod(final Duration first, final Duration second) {
		Check.notNull(first, "The first duration cannot be null!");
		Check.notNull(second, "The second duration cannot be null!");
		return Duration.ofTicks(first.get() * second.get());
	}

	/** TODO: Documentation */
	public static Duration quot(final Pair<Duration, Duration> durations) {
		Check.notNull(durations, "The durations pair cannot be null!");
		return Duration.quot(durations.getFirst(), durations.getSecond());
	}

	/** TODO: Documentation */
	public static Duration quot(final Duration first, final Duration second) {
		Check.notNull(first, "The first duration cannot be null!");
		Check.notNull(second, "The second duration cannot be null!");
		return Duration.ofTicks(first.get() / second.get());
	}
}
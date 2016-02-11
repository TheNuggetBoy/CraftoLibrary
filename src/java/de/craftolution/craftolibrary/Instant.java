package de.craftolution.craftolibrary;

import java.time.Duration;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 11.02.2016
 */
public class Instant {

	private final long instant = System.currentTimeMillis();

	/** TODO: Documentation */
	public Instant() { }

	/** TODO: Documentation */
	public boolean hasPassed(final Duration duration) {
		return System.currentTimeMillis() - this.instant > duration.toMillis();
	}

}
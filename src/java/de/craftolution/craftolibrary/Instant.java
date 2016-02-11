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
	public boolean hasPassed(Duration duration) {
		return System.currentTimeMillis() - instant > duration.toMillis();
	}

}
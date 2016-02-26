/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.time.Duration;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 11.02.2016
 */
public class Stopwatch {

	private final long instant = System.currentTimeMillis();

	/** TODO: Documentation */
	Stopwatch() { }

	/** TODO: Documentation */
	public boolean hasPassed(final Duration duration) {
		return System.currentTimeMillis() - this.instant > duration.toMillis();
	}

	/** TODO: Documentation */
	public Duration getPassed() {
		return Duration.ofMillis(System.currentTimeMillis() - this.instant);
	}

	/** TODO: Documentation */
	public static Stopwatch start() { return new Stopwatch(); }

}
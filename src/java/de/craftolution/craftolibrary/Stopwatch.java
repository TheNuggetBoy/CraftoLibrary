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
 * @author Fear837
 * @since 11.02.2016
 */
public class Stopwatch {

	private final long instant = System.nanoTime();

	/** TODO: Documentation */
	Stopwatch() { }

	/** TODO: Documentation */
	public boolean hasPassed(final Duration duration) {
		return System.nanoTime() - this.instant > duration.toNanos();
	}

	/** TODO: Documentation */
	public Duration getPassed() {
		return Duration.ofNanos(System.nanoTime() - this.instant);
	}

	/** TODO: Documentation */
	public static Stopwatch start() { return new Stopwatch(); }

}
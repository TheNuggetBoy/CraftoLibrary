/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.logging;

import java.time.Instant;

import org.apache.log4j.Level;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.09.2015
 */
public class LogRecord {

	private final Logger logger;
	private final Level level;
	private final String message;
	private final Instant instant;

	LogRecord(final Logger logger, final Instant instant, final Level level, final String message) {
		this.logger = logger;
		this.instant = instant;
		this.level = level;
		this.message = message;
	}

	/** TODO: Documentation */
	public Logger getLogger() {
		return this.logger;
	}

	/** TODO: Documentation */
	public Instant getInstant() {
		return this.instant;
	}

	/** TODO: Documentation */
	public Level getLevel() {
		return this.level;
	}

	/** TODO: Documentation */
	public String getMessage() {
		return this.message;
	}

}
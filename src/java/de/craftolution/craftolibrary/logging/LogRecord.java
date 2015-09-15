package de.craftolution.craftolibrary.logging;

import java.util.Date;

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
	private final Date date;

	LogRecord(final Logger logger, final long timestamp, final Level level, final String message) {
		this.logger = logger;
		this.date = new Date(timestamp);
		this.level = level;
		this.message = message;
	}

	/** TODO: Documentation */
	public Logger getLogger() {
		return this.logger;
	}

	/** TODO: Documentation */
	public Date getDate() {
		return this.date;
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
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.log4j.Level;

import de.craftolution.craftolibrary.Builder;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 14.09.2015
 */
public class LoggerBuilder implements Builder<Logger>, Cloneable {

	@Nullable private String name;
	@Nullable private LogWriter writer;
	@Nullable private SimpleDateFormat dateFormatter;
	@Nullable private LogFormatter formatter;

	LoggerBuilder() {
		// Default settings
		this.writer(Logger.getDefaultSystemWriter());
		this.dateFormatter(Logger.getDefaultDateFormatter());
		this.formatter(Logger.getDefaultFormatter());
	}

	// --- Getters ---

	/** TODO: Documentation */
	public Optional<String> getName() { return Optional.ofNullable(this.name); }

	/** TODO: Documentation */
	public Optional<LogWriter> getWriter() { return Optional.ofNullable(this.writer); }

	/** TODO: Documentation */
	public Optional<SimpleDateFormat> getDateFormatter() { return Optional.ofNullable(this.dateFormatter); }

	/** TODO: Documentation */
	public Optional<LogFormatter> getFormatter() { return Optional.ofNullable(this.formatter); }

	// --- Builder methods ---

	/** TODO: Documentation */
	public LoggerBuilder name(final String name) { this.name = name; return this; }

	/** TODO: Documentation */
	public LoggerBuilder writer(final LogWriter writer) { this.writer = writer; return this; }

	/** TODO: Documentation */
	public LoggerBuilder dateFormatter(final String pattern) throws NullPointerException, IllegalArgumentException {
		this.dateFormatter = new SimpleDateFormat(pattern);
		return this;
	}

	/** TODO: Documentation */
	public LoggerBuilder dateFormatter(final SimpleDateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
		return this;
	}

	/** TODO: Documentation */
	public LoggerBuilder formatter(final LogFormatter formatter) {
		this.formatter = formatter;
		return this;
	}

	/** TODO: Documentation */
	public LoggerBuilder output(final OutputStream output, final Level... levels) throws IllegalArgumentException {
		this.writer.provideOutput(output, levels);
		return this;
	}

	/** TODO: Documentation
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException */
	public LoggerBuilder output(final File output, final Level... levels) throws IllegalArgumentException, FileNotFoundException {
		this.writer.provideOutput(output, levels);
		return this;
	}

	@Override
	public LoggerBuilder clone() {
		return new LoggerBuilder()
				.name(this.name)
				.writer(this.writer)
				.dateFormatter(this.dateFormatter)
				.formatter(this.formatter);
	}

	@Override
	public Logger build() throws IllegalStateException {
		if (this.name == null) { throw new IllegalStateException("Cannot build logger because the name is null!"); }

		final LogWriter writer = this.writer != null ? this.writer : Logger.getDefaultSystemWriter();
		final SimpleDateFormat dateFormatter = this.dateFormatter != null ? this.dateFormatter : Logger.getDefaultDateFormatter();
		final LogFormatter formatter = this.formatter != null ? this.formatter : Logger.getDefaultFormatter();

		return new Logger(this.name, writer.start(), dateFormatter, formatter);
	}

}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.logging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.log4j.Level;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 14.09.2015
 */
public class Logger {

	// --- Static fields ---

	private static final Object[] EMPTY_ARRAY = new Object[0];
	private static final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

	private static final LogWriter defaultSystemWriter = new LogWriter()
			.provideOutput(System.err, Level.FATAL, Level.ERROR, Level.WARN)
			.provideOutput(System.out, Level.INFO);

	private static final LogFormatter defaultFormatter = record -> {
		return new StringBuilder()
				.append('[')
				.append(record.getLogger().getDateFormatter().format(record.getDate()))
				.append("] [")
				.append(record.getLogger().getName())
				.append("] [")
				.append(record.getLevel().toString())
				.append("]: ")
				.append(record.getMessage()).toString();
	};

	private static final SimpleDateFormat defaultDateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS");

	/** TODO: Documentation */
	public static LogWriter getDefaultSystemWriter() { return Logger.defaultSystemWriter; }

	/** TODO: Documentation */
	public static LogFormatter getDefaultFormatter() { return Logger.defaultFormatter; }

	/** TODO: Documentation */
	public static SimpleDateFormat getDefaultDateFormatter() { return Logger.defaultDateFormatter; }

	// --- Factory methods ---

	/** TODO: Documentation */
	public static Logger of(final String name) {
		Logger logger = Logger.loggerMap.get(name);
		if (logger == null) {
			logger = Logger.builder(name).build();
			Logger.loggerMap.put(name, logger);
		}

		return logger;
	}

	/** TODO: Documentation */
	public static LoggerBuilder builder() {
		return new LoggerBuilder();
	}

	/** TODO: Documentation */
	public static LoggerBuilder builder(final String name) {
		return new LoggerBuilder().name(name);
	}

	// --- Fields ---

	private final String name;
	private final LogWriter writer;
	private final SimpleDateFormat dateFormatter;
	private final LogFormatter formatter;

	private final Map<Level, List<Consumer<LogRecord>>> levelListeners;
	private final List<Consumer<LogRecord>> listeners;

	Logger(final Logger logger, final String channelName) {
		this.name = channelName;
		this.writer = logger.writer;
		this.dateFormatter = logger.dateFormatter;
		this.formatter = logger.formatter;
		this.levelListeners = logger.levelListeners;
		this.listeners = logger.listeners;
	}

	Logger(final String name, final LogWriter worker, final SimpleDateFormat dateFormatter, final LogFormatter formatter) {
		Check.nonNulls("The name/worker/dateFormatter/formatter cannot be null!", name, worker, dateFormatter, formatter);
		this.name = name;
		this.writer = worker;
		this.dateFormatter = dateFormatter;
		this.formatter = formatter;
		this.levelListeners = new HashMap<>();
		this.listeners = new ArrayList<Consumer<LogRecord>>();
	}

	// --- Getters ---

	/** TODO: Documentation */
	public String getName() { return this.name; }

	/** TODO: Documentation */
	public Set<Level> getSupportedLevels() { return this.writer.getSupportedLevels(); }

	/** TODO: Documentation */
	public boolean supportsLevel(final Level level) { return this.writer.supportsLevel(level); }

	/** TODO: Documentation */
	public SimpleDateFormat getDateFormatter() { return this.dateFormatter; }

	/** TODO: Documentation */
	public LogFormatter getFormatter() { return this.formatter; }

	// --- Listener methods ---

	/** TODO: Documentation */
	public Logger addListener(final Level level, final Consumer<LogRecord> listener) {
		Check.nonNulls("The level/listener cannot be null!", level, listener);
		if (!this.levelListeners.containsKey(level)) {
			this.levelListeners.put(level, new ArrayList<Consumer<LogRecord>>());
		}
		this.levelListeners.get(level).add(listener);
		return this;
	}

	/** TODO: Documentation */
	public Logger addListener(final Consumer<LogRecord> listener) {
		Check.notNull(listener, "The listener cannot be null!");
		this.listeners.add(listener);
		return this;
	}

	// --- Logger methods ---

	/** TODO: Documentation */
	public Logger log(final Level level, final CharSequence message, @Nullable final Object... replacements) {
		Check.nonNulls("The level/message cannot be null!", level, message);
		if (!this.writer.supportsLevel(level)) { return this; }

		// Replace replacements
		String stringMessage = message.toString();
		if (replacements != null && replacements.length > 0)  {
			for (final Object obj : replacements) {
				if (obj != null) {
					stringMessage = stringMessage.replace("%?", obj.toString());
				}
				else { stringMessage = stringMessage.replace("%?", "null"); }
			}
		}

		final LogRecord record = new LogRecord(this, System.currentTimeMillis(), level, stringMessage);

		// Notify event handlers
		this.listeners.forEach(handler -> handler.accept(record));
		if (this.levelListeners.containsKey(level)) {
			this.levelListeners.get(level).forEach(handler -> handler.accept(record));
		}

		this.writer.insertRecord(record);
		return this;
	}

	/** TODO: Documentation */
	public Logger log(final Level level, final CharSequence message) {
		return this.log(level, message, Logger.EMPTY_ARRAY);
	}

	/** TODO: Documentation */
	public Logger info(final CharSequence message) {
		return this.log(Level.INFO, message);
	}

	/** TODO: Documentation */
	public Logger warn(final CharSequence message) {
		return this.log(Level.WARN, message);
	}

	/** TODO: Documentation */
	public Logger error(final CharSequence message) {
		return this.log(Level.ERROR, message);
	}

	/** TODO: Documentation */
	public Logger fatal(final CharSequence message) {
		return this.log(Level.FATAL, message);
	}

	/** TODO: Documentation */
	public Logger debug(final CharSequence message) {
		return this.log(Level.DEBUG, message);
	}

	/** TODO: Documentation */
	public Logger trace(final CharSequence message) {
		return this.log(Level.TRACE, message);
	}

	/** TODO: Documentation */
	public Logger info(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}

	/** TODO: Documentation */
	public Logger warn(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}

	/** TODO: Documentation */
	public Logger error(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}

	/** TODO: Documentation */
	public Logger fatal(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}

	/** TODO: Documentation */
	public Logger debug(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}

	/** TODO: Documentation */
	public Logger trace(final CharSequence message, @Nullable final Object... replacements) {
		return this.log(Level.INFO, message, replacements);
	}
}
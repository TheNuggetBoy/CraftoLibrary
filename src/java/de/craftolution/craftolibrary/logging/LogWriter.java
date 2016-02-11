package de.craftolution.craftolibrary.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Level;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.09.2015
 */
public class LogWriter implements Runnable {

	// --- Static ---

	private static final AtomicInteger WORKER_COUNT = new AtomicInteger();

	// --- Fields ---

	private final LinkedBlockingQueue<LogRecord> queue;
	private final Map<Level, List<Writer>> outputMap;
	private final AtomicBoolean running;

	LogWriter() {
		this.queue = new LinkedBlockingQueue<LogRecord>();
		this.running = new AtomicBoolean(false);
		this.outputMap = new HashMap<>();
	}

	/** TODO: Documentation */
	protected Writer createWriter(final OutputStream output) {
		return new BufferedWriter(new OutputStreamWriter(output));
	}

	/** TODO: Documentation */
	public LogWriter insertRecord(final LogRecord record) {
		//Check.notnul
		try { this.queue.put(record); }
		catch (final InterruptedException e) { e.printStackTrace(); }
		return this;
	}

	/** TODO: Documentation */
	public LogWriter provideOutput(final OutputStream output, final Level... levels) throws IllegalArgumentException {
		Check.nonNulls("The output/levels cannot be null!", output, levels);
		Check.notEmpty(levels, "The given array of levels cannot be empty!");
		for (final Level level : levels) {
			if (!this.outputMap.containsKey(level)) { this.outputMap.put(level, new ArrayList<Writer>()); }
			this.outputMap.get(level).add(this.createWriter(output));
		}
		return this;
	}

	/** TODO: Documentation
	 * @throws FileNotFoundException */
	public LogWriter provideOutput(final File file, final Level... levels) throws IllegalArgumentException, FileNotFoundException {
		return this.provideOutput(new FileOutputStream(file, true), levels);
	}

	/** TODO: Documentation */
	public LogWriter start() {
		if (!this.running.get()) {
			new Thread(this, "LoggerWorker-" + LogWriter.WORKER_COUNT.getAndIncrement()).start();
		}
		return this;
	}

	/** TODO: Documentation */
	public boolean isRunning() { return this.running.get(); }

	/** TODO: Documentation */
	public LogWriter stop() { this.running.set(false); return this; }

	/** TODO: Documentation */
	public Set<Level> getSupportedLevels() { return this.outputMap.keySet(); }

	/** TODO: Documentation */
	public boolean supportsLevel(final Level level) { return this.outputMap.containsKey(level); }

	@Override
	public void run() {
		this.running.set(true);

		while (this.running.get()) {
			try {
				final LogRecord record = this.queue.take();

				// Format logMessage
				final String logMessage = record.getLogger().getFormatter().format(record);

				// Write into outputs
				for (final Writer writer : this.outputMap.get(record.getLevel())) {
					try {
						writer.write(logMessage);
						writer.write(System.lineSeparator());
						writer.flush();
					}
					catch (final IOException e) { e.printStackTrace(); }
				}
			}
			catch (final InterruptedException e1) { e1.printStackTrace(); }
		}
	}
}
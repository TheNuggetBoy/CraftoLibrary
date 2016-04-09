package de.craftolution.craftolibrary.database;

import java.sql.Statement;
import java.time.Duration;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.google.common.collect.Queues;

import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.Scheduled;
import de.craftolution.craftolibrary.Stopwatch;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.table.Table;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.02.2016
 */
public class StatisticRecorder extends TimerTask {

	private static final String TABLE_NAME = "cl_stats_database_queries";

	private final Database database;
	private final AtomicBoolean recording;
	private final Consumer<String> logger;
	private final Consumer<Exception> exceptionHandler;
	private final Queue<QueryResult> queue = Queues.newConcurrentLinkedQueue();
	private final Timer timer;

	StatisticRecorder(final Database database, final boolean enabled, final Consumer<String> logger, final Consumer<Exception> exceptionHandler) {
		this.database = database;
		this.recording = new AtomicBoolean(enabled);
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;

		if (enabled) {
			this.timer = new Timer("DatabaseStatisticRecorder", true);

			final Table table = Table.builder(StatisticRecorder.TABLE_NAME)
					.addString("query", null)
					.addInt("duration", c -> c.length(6))
					.addBoolean("success", null)
					.addInt("affected_rows", c -> c.length(7))
					.addCreatedAt()
					.build();

			final Result<Statement> result = this.database.createTable(table);
			if (!result.getException().isPresent()) {
				this.logger.accept("Disabling the StatisticRecorder because failed to create the database table " + StatisticRecorder.TABLE_NAME + ".");
				this.recording.set(false);
			}

		}
		else { this.timer = null; }
	}

	void start() {
		synchronized (this.recording) {
			if (this.recording.get()) { return; }
			this.timer.scheduleAtFixedRate(this, 1000L, 1000L * 60 * 30);
			this.recording.set(true);
		}
	}

	void stop() {
		synchronized (this.recording) {
			if (!this.recording.get()) { return; }
			this.cancel();
			this.recording.set(false);
		}
	}

	void insertQuery(final QueryResult result) {
		synchronized (this.recording) {
			if (!this.recording.get() || this.queue.size() > 102400) { return; }
			if (result.getQuery().toString().contains(StatisticRecorder.TABLE_NAME)) { return; }

			this.queue.add(result);
		}
	}

	@Override
	public void run() {
		if (!this.recording.get() || this.queue.isEmpty()) { return; }

		final StringBuilder builder = new StringBuilder("INSERT INTO `").append(StatisticRecorder.TABLE_NAME).append("` (`query`, `duration`, `success`, `affected_rows`, `created_at`) VALUES ('");
		final Stopwatch stopWatch = Stopwatch.start();

		for (QueryResult result = this.queue.poll(); !this.queue.isEmpty() && !stopWatch.hasPassed(Duration.ofSeconds(2)); result = this.queue.poll()) {
			builder.append(result.getQuery().toString()).append("', ")
			.append(result.getExecutionDuration().toMillis()).append(", ")
			.append(result.wasSuccess()).append(", ")
			.append(result.getAffectedRows()).append(", '")
			.append(System.currentTimeMillis()).append("'), ('");
		}

		builder.delete(builder.length() - 4, builder.length()).append(';');

		final Scheduled<QueryResult> result = this.database.executeAsync(Query.of(builder.toString()));
		result.addListener(queryResult -> queryResult.getException().ifPresent(e -> this.exceptionHandler.accept(e)));
	}

}
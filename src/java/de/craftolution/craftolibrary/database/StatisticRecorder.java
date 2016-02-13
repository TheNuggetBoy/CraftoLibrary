package de.craftolution.craftolibrary.database;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 12.02.2016
 */
public class StatisticRecorder {

	private boolean enabled;

	private final AtomicInteger successfulQueries = new AtomicInteger(0);
	private final AtomicInteger failedQueries = new AtomicInteger(0);

	private final AtomicLong minuteStart = new AtomicLong(System.currentTimeMillis());
	private final AtomicInteger queriesThisMinute = new AtomicInteger(0);
	private final AtomicInteger countedQueriesPerMinute = new AtomicInteger(0);
	private final AtomicInteger averageQueriesPerMinute = new AtomicInteger(0);

	private final AtomicInteger countedDurations = new AtomicInteger(0);
	private final AtomicLong averageDuration = new AtomicLong();

	StatisticRecorder(Database database, boolean enabled) {
		this.enabled = enabled;
	}

	void insertQuery(QueryResult result) {
		if (!enabled) { return; }

		if (result.wasSuccess()) { this.successfulQueries.incrementAndGet(); }
		else { this.failedQueries.incrementAndGet(); }

		if (System.currentTimeMillis() - this.minuteStart.get() > 1000) {
			this.averageQueriesPerMinute.set((this.averageQueriesPerMinute.get() * (this.countedQueriesPerMinute.get() - 1) + this.queriesThisMinute.get()) / this.countedQueriesPerMinute.get());
			this.queriesThisMinute.set(0);
			this.minuteStart.set(System.currentTimeMillis());
			this.countedQueriesPerMinute.incrementAndGet();
		}
		else { this.queriesThisMinute.incrementAndGet(); }

		if (!result.getExecutionDuration().isZero()) {
			this.averageDuration.set((this.averageDuration.get() * (this.countedDurations.get() - 1) + result.getExecutionDuration().toNanos()) / this.countedDurations.get());
			this.countedDurations.incrementAndGet();
		}
	}

	/** TODO: Documentation */
	public int getQueriesPerMinute() { return this.averageQueriesPerMinute.get(); }

	/** TODO: Documentation */
	public Duration getAverageDuration() { return Duration.ofNanos(this.averageDuration.get()); }

}
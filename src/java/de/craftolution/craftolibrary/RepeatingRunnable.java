package de.craftolution.craftolibrary;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 18.03.2016
 */
public class RepeatingRunnable {

	private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

	private final Duration interval;
	private final Runnable runnable;

	private Runnable cancelListener;

	RepeatingRunnable(Duration interval, Duration initialDelay, Runnable runnable) {
		this.interval = interval;
		this.runnable = runnable;
		executor.scheduleWithFixedDelay(runnable, initialDelay.toNanos(), interval.toNanos(), TimeUnit.NANOSECONDS);
	}

	/** TODO: Documentation */
	public boolean cancel() {
		boolean result = executor.remove(this.runnable);
		if (cancelListener != null) { cancelListener.run(); }
		return result;
	}

	/** TODO: Documentation */
	public RepeatingRunnable cancelAfter(Duration delay) { executor.schedule(() -> cancel(), delay.toNanos(), TimeUnit.NANOSECONDS); return this; }

	/** TODO: Documentation */
	public RepeatingRunnable cancelAfter(Instant instant) { executor.schedule(() -> cancel(), instant.toEpochMilli() - Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS); return this; }

	/** TODO: Documentation */
	public RepeatingRunnable onCancel(Runnable onCancel) { this.cancelListener = onCancel; return this; }

	/** TODO: Documentation */
	public Duration getInterval() { return this.interval; }

	/** TODO: Documentation */
	public Runnable getRunnable() { return this.runnable; }

	/** TODO: Documentation */
	public static RepeatingRunnable run(Duration interval, Runnable runnable) {
		return new RepeatingRunnable(interval, Duration.ZERO, runnable);
	}

	/** TODO: Documentation */
	public static RepeatingRunnable run(Duration interval, Duration initialDelay, Runnable runnable) {
		return new RepeatingRunnable(interval, initialDelay, runnable);
	}

}
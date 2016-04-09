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

	RepeatingRunnable(final Duration interval, final Duration initialDelay, final Runnable runnable) {
		this.interval = interval;
		this.runnable = runnable;
		RepeatingRunnable.executor.scheduleWithFixedDelay(runnable, initialDelay.toNanos(), interval.toNanos(), TimeUnit.NANOSECONDS);
	}

	/** TODO: Documentation */
	public boolean cancel() {
		final boolean result = RepeatingRunnable.executor.remove(this.runnable);
		if (this.cancelListener != null) { this.cancelListener.run(); }
		return result;
	}

	/** TODO: Documentation */
	public RepeatingRunnable cancelAfter(final Duration delay) { RepeatingRunnable.executor.schedule(() -> this.cancel(), delay.toNanos(), TimeUnit.NANOSECONDS); return this; }

	/** TODO: Documentation */
	public RepeatingRunnable cancelAfter(final Instant instant) { RepeatingRunnable.executor.schedule(() -> this.cancel(), instant.toEpochMilli() - Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS); return this; }

	/** TODO: Documentation */
	public RepeatingRunnable onCancel(final Runnable onCancel) { this.cancelListener = onCancel; return this; }

	/** TODO: Documentation */
	public Duration getInterval() { return this.interval; }

	/** TODO: Documentation */
	public Runnable getRunnable() { return this.runnable; }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Runnable runnable) {
		return new RepeatingRunnable(interval, Duration.ZERO, runnable);
	}

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Duration initialDelay, final Runnable runnable) {
		return new RepeatingRunnable(interval, initialDelay, runnable);
	}

}
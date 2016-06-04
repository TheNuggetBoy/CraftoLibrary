package de.craftolution.craftolibrary;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.Nullable;

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
	private final Consumer<RepeatingRunnable> consumer;
	private final Runnable realRunnable = this::run;

	private Runnable cancelListener;

	private final int maxExecutions;
	private int executions;

	RepeatingRunnable(final Duration interval, final Duration initialDelay, final Runnable runnable, final int maxExecutions) {
		this.interval = interval;
		this.runnable = runnable;
		this.consumer = null;
		this.maxExecutions = maxExecutions;
		RepeatingRunnable.executor.scheduleWithFixedDelay(this.realRunnable, initialDelay.toNanos(), interval.toNanos(), TimeUnit.NANOSECONDS);
	}

	RepeatingRunnable(final Duration interval, final Duration initialDelay, final Consumer<RepeatingRunnable> consumer, final int maxExecutions) {
		this.interval = interval;
		this.runnable = null;
		this.consumer = consumer;
		this.maxExecutions = maxExecutions;
		RepeatingRunnable.executor.scheduleWithFixedDelay(this.realRunnable, initialDelay.toNanos(), interval.toNanos(), TimeUnit.NANOSECONDS);
	}

	/** TODO: Documentation */
	public void run() {
		if (this.maxExecutions == -1 || this.executions < this.maxExecutions) {
			this.executions++;
			if (this.runnable != null) { this.runnable.run(); }
			else if (this.consumer != null) { this.consumer.accept(this); }
		}
	}

	/** TODO: Documentation */
	public boolean cancel() {
		final boolean result = RepeatingRunnable.executor.remove(this.realRunnable);
		if (this.cancelListener != null) { this.cancelListener.run(); }
		System.out.println("cancelled");
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
	public int getExecutionCount() { return this.executions; }

	/** TODO: Documentation */
	public int getMaxExecutions() { return this.maxExecutions; }

	/** TODO: Documentation */
	@Nullable public Runnable getRunnable() { return this.runnable; }

	/** TODO: Documentation */
	@Nullable public Consumer<RepeatingRunnable> getConsumer() { return this.consumer; }

	/** TODO: Documentation */
	public static RepeatingRunnable runOnce(final Duration delay, final Runnable runnable) { return new RepeatingRunnable(Duration.ofMillis(1), delay, runnable, 1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Runnable runnable) { return new RepeatingRunnable(interval, Duration.ZERO, runnable, -1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Duration initialDelay, final Runnable runnable) { return new RepeatingRunnable(interval, initialDelay, runnable, -1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Duration initialDelay, final int maxExecutions, final Runnable runnable) {
		return new RepeatingRunnable(interval, initialDelay, runnable, maxExecutions);
	}

	/** TODO: Documentation */
	public static RepeatingRunnable runOnce(final Duration delay, final Consumer<RepeatingRunnable> consumer) { return new RepeatingRunnable(Duration.ofMillis(1), delay, consumer, 1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Consumer<RepeatingRunnable> consumer) { return new RepeatingRunnable(interval, Duration.ZERO, consumer, -1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Duration initialDelay, final Consumer<RepeatingRunnable> consumer) { return new RepeatingRunnable(interval, initialDelay, consumer, -1); }

	/** TODO: Documentation */
	public static RepeatingRunnable run(final Duration interval, final Duration initialDelay, final int maxExecutions, final Consumer<RepeatingRunnable> consumer) {
		return new RepeatingRunnable(interval, initialDelay, consumer, maxExecutions);
	}

}
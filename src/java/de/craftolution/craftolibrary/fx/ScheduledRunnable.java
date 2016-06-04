package de.craftolution.craftolibrary.fx;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class ScheduledRunnable {

	private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
	
	private final Timeline timeline;
	private final Duration interval;
	private final int maxExecutions;
	private final Runnable runnable;
	private final Consumer<ScheduledRunnable> consumer;

	private Runnable cancelListener;
	private int executionCount;

	ScheduledRunnable(final Duration interval, final Duration initialDelay, final int maxExecutions, final Runnable runnable) {
		this.interval = interval;
		this.maxExecutions = maxExecutions;
		this.runnable = runnable;
		this.consumer = null;
		this.timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(interval.toMillis()), event -> this.run()));
		this.timeline.setDelay(javafx.util.Duration.millis(initialDelay.toMillis()));
		this.timeline.setCycleCount(maxExecutions);
		this.timeline.play();
	}
	
	ScheduledRunnable(final Duration interval, final Duration initialDelay, final int maxExecutions, final Consumer<ScheduledRunnable> consumer) {
		this.interval = interval;
		this.maxExecutions = maxExecutions;
		this.runnable = null;
		this.consumer = consumer;
		this.timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(interval.toMillis()), event -> this.run()));
		this.timeline.setDelay(javafx.util.Duration.millis(initialDelay.toMillis()));
		this.timeline.setCycleCount(maxExecutions);
		this.timeline.play();
	}

	/** TODO: Documentation */
	public void cancel() {
		this.timeline.stop();
		if (this.cancelListener != null) { this.cancelListener.run(); }
	}

	/** TODO: Documentation */
	public ScheduledRunnable cancelAfter(final Duration delay) { ScheduledRunnable.executor.schedule(() -> this.cancel(), delay.toNanos(), TimeUnit.NANOSECONDS); return this; }

	/** TODO: Documentation */
	public ScheduledRunnable cancelAfter(final Instant instant) { ScheduledRunnable.executor.schedule(() -> this.cancel(), instant.toEpochMilli() - Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS); return this; }

	/** TODO: Documentation */
	public ScheduledRunnable onCancel(final Runnable onCancel) { this.cancelListener = onCancel; return this; }

	/** TODO: Documentation */
	public void run() {
		if (this.maxExecutions == -1 || this.executionCount < this.maxExecutions) {
			this.executionCount++;
			if (this.runnable != null) { this.runnable.run(); }
			else if (this.consumer != null) { this.consumer.accept(this); }
		}
	}

	/** TODO: Documentation */
	public Duration getInterval() { return this.interval; }

	/** TODO: Documentation */
	public int getExecutionCount() { return this.executionCount; }

	/** TODO: Documentation */
	public int getMaxExecutions() { return this.maxExecutions; }

	/** TODO: Documentation */
	@Nullable public Runnable getRunnable() { return this.runnable; }

	/** TODO: Documentation */
	@Nullable public Consumer<ScheduledRunnable> getConsumer() { return this.consumer; }

	/** TODO: Documentation */
	public static ScheduledRunnable runOnce(final Duration delay, final Runnable runnable) {
		return new ScheduledRunnable(Duration.ofMillis(1), delay, 1, runnable);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Runnable runnable) {
		return new ScheduledRunnable(interval, Duration.ZERO, -1, runnable);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Duration initialDelay, final Runnable runnable) {
		return new ScheduledRunnable(interval, initialDelay, -1, runnable);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Duration initialDelay, final int maxExecutions, final Runnable runnable) {
		return new ScheduledRunnable(interval, initialDelay, maxExecutions, runnable);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable runOnce(final Duration delay, final Consumer<ScheduledRunnable> consumer) {
		return new ScheduledRunnable(Duration.ofMillis(1), delay, 1, consumer);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Consumer<ScheduledRunnable> consumer) {
		return new ScheduledRunnable(interval, Duration.ZERO, -1, consumer);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Duration initialDelay, final Consumer<ScheduledRunnable> consumer) {
		return new ScheduledRunnable(interval, initialDelay, -1, consumer);
	}

	/** TODO: Documentation */
	public static ScheduledRunnable run(final Duration interval, final Duration initialDelay, final int maxExecutions, final Consumer<ScheduledRunnable> consumer) {
		return new ScheduledRunnable(interval, initialDelay, maxExecutions, consumer);
	}

}
/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * TODO: File description for ScheduledResult.java
 *
 * @param <E> -
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 25.07.2015 18:29:44
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class Scheduled<E> implements Supplier<E>, com.google.common.base.Supplier<E>, ListenableFuture<E> {

	// --- Computation Stuff ---

	@Nullable private E result;
	@Nullable private ExecutionException exception;
	@Nullable private Thread computationThread;
	private final AtomicBoolean computationRunning;
	private final Supplier<E> resultSupplier;

	// --- Listeners ---

	private final ArrayList<Consumer<E>> consumerListeners;
	private final ArrayList<Runnable> runnableListeners;

	private final ArrayList<Pair<Runnable, Executor>> executorRunnableListener;

	private final ArrayList<Pair<Consumer<E>, Consumer<Runnable>>> consumerExecutorConsumerListener;
	private final ArrayList<Pair<Runnable, Consumer<Runnable>>> consumerExecutorRunnableListener;

	// --- ListenableFuture stuff ---
	private final AtomicBoolean done;
	private final AtomicBoolean cancelled;

	private Scheduled(final Duration delay, final Consumer<Runnable> executor, final Supplier<E> resultSupplier, @Nullable final Consumer<E> firstListener, @Nullable final Runnable secondListener) {
		this.resultSupplier = resultSupplier;
		this.computationRunning = new AtomicBoolean(false);

		this.consumerListeners = new ArrayList<>(1);
		this.runnableListeners = new ArrayList<>(1);
		this.executorRunnableListener = new ArrayList<>(1);
		this.consumerExecutorConsumerListener = new ArrayList<>(1);
		this.consumerExecutorRunnableListener = new ArrayList<>(1);

		this.done = new AtomicBoolean(false);
		this.cancelled = new AtomicBoolean(false);

		if (firstListener != null) { this.consumerListeners.add(firstListener); }
		if (secondListener != null) { this.runnableListeners.add(secondListener); }

		if (delay.isZero()) {
			executor.accept(() -> this.compute());
		}
		else { executor.accept(() -> this.computeDelayed(delay)); }
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final Consumer<Runnable> executor, final Supplier<E> resultSupplier) {
		this(delay, executor, resultSupplier, null, null);
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final Consumer<Runnable> executor, final Supplier<E> resultSupplier, final Consumer<E> listener) {
		this(delay, executor, resultSupplier, listener, null);
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final Consumer<Runnable> executor, final Supplier<E> resultSupplier, final Runnable listener) {
		this(delay, executor, resultSupplier, null, listener);
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final boolean async, final Supplier<E> resultSupplier) {
		this(delay, async ? Threads::go : r -> r.run(), resultSupplier, null, null);
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final boolean async, final Supplier<E> resultSupplier, final Consumer<E> listener) {
		this(delay, async ? Threads::go : r -> r.run(), resultSupplier, listener, null);
	}

	/** TODO: Documentation */
	public Scheduled(final Duration delay, final boolean async, final Supplier<E> resultSupplier, final Runnable listener) {
		this(delay, async ? Threads::go : r -> r.run(), resultSupplier, null, listener);
	}

	private void computeDelayed(final Duration delay) {
		// Wait
		try { Thread.sleep(delay.toMillis()); }
		catch (final InterruptedException e) {
			this.exception = new ExecutionException("The scheduled result " + this.toString() + " was interrupted on computation.", e);
			this.cancelled.set(true);
			return;
		}

		this.compute();
	}

	private void compute() {
		// Execute
		try {
			this.computationThread = Thread.currentThread();
			this.computationRunning.set(true);
			this.result = this.resultSupplier.get();
			this.computationRunning.set(false);
		}
		catch (final Exception e) {
			this.exception = new ExecutionException("The scheduled result " + this.toString() + " threw an exception on computation.", e);
		}
		finally { this.done.set(true); }

		// Notify listeners
		this.notifyListeners();
	}

	private void notifyListeners() {
		this.consumerListeners.forEach(c -> c.accept(this.result));
		this.runnableListeners.forEach(r -> r.run());
		this.executorRunnableListener.forEach(pair -> pair.getSecond().execute(pair.getFirst()));
		this.consumerExecutorConsumerListener.forEach(pair -> pair.getSecond().accept(() -> pair.getFirst().accept(this.result)));
		this.consumerExecutorRunnableListener.forEach(pair -> pair.getSecond().accept(() -> pair.getFirst().run()));
	}

	/** TODO: Documentation */
	@Override @Nullable public E get() { return this.result; }

	/** TODO: Documentation */
	@Override @Nonnull public E get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		final long start = System.nanoTime();
		boolean run = true;
		while (run && System.nanoTime() - start < unit.toNanos(timeout)) {
			if (this.isCancelled()) {
				run = false;
				throw new CancellationException("The scheduled result " + this.toString() + " was cancelled.");
			}
			if (this.isDone() || this.result != null) {
				run = false;
				if (this.result == null && this.exception != null) {
					throw this.exception;
				}
				return this.result;
			}
		}
		throw new TimeoutException("The scheduled result " + this.toString() + " took too long.");
	}

	/** TODO: Documentation */
	public void addListener(final Consumer<E> listener) {
		Check.notNull(listener, "The listener cannot be null!");
		this.consumerListeners.add(listener);
	}

	/** TODO: Documentation */
	public void addListener(final Runnable listener) {
		Check.notNull(listener, "The listener cannot be null!");
		this.runnableListeners.add(listener);
	}

	@Override
	public void addListener(final Runnable listener, final Executor executor) {
		Check.nonNulls("The listener/executor cannot be null!", listener, executor);
		this.executorRunnableListener.add(Pair.of(listener, executor));
	}

	/** TODO: Documentation */
	public void addListener(final Consumer<E> listener, final Consumer<Runnable> executor) {
		Check.nonNulls("The listener/executor cannot be null!", listener, executor);
		this.consumerExecutorConsumerListener.add(Pair.of(listener, executor));
	}

	/** TODO: Documentation */
	public void addListener(final Runnable listener, final Consumer<Runnable> executor) {
		Check.nonNulls("The listener cannot be null!", listener, executor);
		this.consumerExecutorRunnableListener.add(Pair.of(listener, executor));
	}

	@Override
	public boolean cancel(final boolean mayInterruptIfRunning) { // TODO: Handle mayInterruptIfRunning
		this.cancelled.set(true);
		if (mayInterruptIfRunning && this.computationRunning.get() && this.computationThread != null) {
			this.computationThread.interrupt();
			this.computationRunning.set(false);
		}
		return !this.done.get();
	}

	@Override
	public boolean isCancelled() { return this.cancelled.get(); }

	@Override
	public boolean isDone() { return this.done.get(); }

}
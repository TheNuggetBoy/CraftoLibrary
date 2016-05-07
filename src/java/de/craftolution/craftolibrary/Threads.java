/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * TODO: File description for Threads.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 25.07.2015 17:39:04
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class Threads {

	static ExecutorService service = Executors.newCachedThreadPool();

	private Threads() { }
	
	/** TODO: Documentation */
	public static void sleep(Duration duration) {
		try { Thread.sleep(duration.toMillis()); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	/** TODO: Documentation */
	public static void go(final Runnable runnable) {
		Threads.service.execute(runnable);
	}

	/** TODO: Documentation */
	public static void go(final Supplier<?> supplier) {
		Threads.go(() -> supplier.get());
	}

	/** TODO: Documentation */
	public static <E> void go(final E parameter, final Consumer<E> consumer) {
		Threads.go(() -> consumer.accept(parameter));
	}

	/** TODO: Documentation */
	public static <T, U> void go(final T firstParameter, final U secondParameter, final BiConsumer<T, U> consumer) {
		Threads.go(() -> consumer.accept(firstParameter, secondParameter));
	}

	/** TODO: Documentation */
	public static <T, R> Scheduled<R> go(final T parameter, final Function<T, R> function) {
		return new Scheduled<R>(Duration.ofMillis(1), Threads::go, () -> function.apply(parameter));
	}

	/** TODO: Documentation */
	public static <T> Scheduled<T> go(final T parameter, final UnaryOperator<T> operator) {
		return new Scheduled<T>(Duration.ofMillis(1), Threads::go, () -> operator.apply(parameter));
	}

	/** TODO: Documentation */
	public static <T, U, R> Scheduled<R> go(final T firstParameter, final U secondParameter, final BiFunction<T, U, R> function) {
		return new Scheduled<R>(Duration.ofMillis(1), Threads::go, () -> function.apply(firstParameter, secondParameter));
	}

	/** TODO: Documentation */
	public static void shutdown() {
		Threads.service.shutdownNow();
		try {
			Threads.service.awaitTermination(2048, TimeUnit.MILLISECONDS);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
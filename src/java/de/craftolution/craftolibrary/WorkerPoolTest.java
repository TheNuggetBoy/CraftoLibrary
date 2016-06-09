package de.craftolution.craftolibrary;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 07.06.2016
 */
class WorkerPoolTest {

	public static void main(final String[] args) {
		final int[] array = new int[2048];
		for (int i = 0; i < array.length; i++) {
			array[i] = i + 1;
		}

		WorkerPoolTest.multi(array);
		WorkerPoolTest.single(array);
	}

	public static void multi(final int[] array) {
		final AtomicInteger foundIndex = new AtomicInteger(-1);
		final AtomicLong start = new AtomicLong();

		final WorkerPool pool = new WorkerPool().addWorker(w -> {
			final int startIndex = array.length / 4 * w.id;
			final int quarter = array.length / 4;
			for (int i = startIndex; i < startIndex + quarter; i++) {
				w.check();
				final int value = array[i];
				w.check();
				if (value == 2033) { foundIndex.set(i); w.finish(); break; }
				w.check();
			}
		}).addWorker(w -> {
			final int startIndex = array.length / 4 * w.id;
			final int quarter = array.length / 4;
			for (int i = startIndex; i < startIndex + quarter; i++) {
				w.check();
				final int value = array[i];
				w.check();
				if (value == 2033) { foundIndex.set(i); w.finish(); break; }
				w.check();
			}
		}).addWorker(w -> {
			final int startIndex = array.length / 4 * w.id;
			final int quarter = array.length / 4;
			for (int i = startIndex; i < startIndex + quarter; i++) {
				w.check();
				final int value = array[i];
				w.check();
				if (value == 2033) { foundIndex.set(i); w.finish(); break; }
				w.check();
			}
		}).addWorker(w -> {
			final int startIndex = array.length / 4 * w.id;
			final int quarter = array.length / 4;
			for (int i = startIndex; i < startIndex + quarter; i++) {
				w.check();
				final int value = array[i];
				w.check();
				if (value == 2033) { foundIndex.set(i); w.finish(); break; }
				w.check();
			}
		}).onFinish(p -> {
			System.out.println("Finished after " + (System.nanoTime() - start.get()) + " multi nanos! " + foundIndex.get());
		});

		start.set(System.nanoTime());
		pool.start();
	}

	public static void single(final int[] array) {
		int foundIndex = -1;
		final long start = System.nanoTime();
		for (int i = 0; i < array.length; i++) {
			final int value = array[i];
			if (value == 2033) { foundIndex = i; break; }
		}
		System.out.println("Finished after " + (System.nanoTime() - start) + " single nanos! " + foundIndex);
	}

}
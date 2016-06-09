package de.craftolution.craftolibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 07.06.2016
 */
public class WorkerPool {

	private final List<Worker> workerList = new ArrayList<>();
	private final AtomicBoolean running = new AtomicBoolean();
	private final ExecutorService threadPool;
	private Consumer<WorkerPool> handler;

	/** TODO: Documentation */
	public WorkerPool() { this.threadPool = Executors.newCachedThreadPool(); }

	/** TODO: Documentation */
	public WorkerPool(final int workerCount) { this.threadPool = Executors.newFixedThreadPool(workerCount); }

	/** TODO: Documentation */
	public WorkerPool addWorker(final Consumer<Worker> c) {
		final Worker worker = new Worker(this, this.workerList.size(), c);
		this.workerList.add(worker);
		return this;
	}

	/** TODO: Documentation */
	public WorkerPool addWorker(final Worker worker) {
		this.workerList.add(worker);
		return this;
	}

	/** TODO: Documentation */
	public WorkerPool onFinish(final Consumer<WorkerPool> handler) {
		this.handler = handler;
		return this;
	}

	/** TODO: Documentation */
	public WorkerPool start() {
		synchronized (this.running) {
			if (!this.running.get()) {
				this.running.set(true);

				for (final Worker worker : this.workerList) {
					worker.start(this.threadPool);
				}
			}
		}
		return this;
	}

	/** TODO: Documentation */
	public boolean isRunning() {
		boolean result = false;
		synchronized (this.running) {
			result = this.running.get();
		}
		return result;
	}

	/** TODO: Documentation */
	public WorkerPool finish() {
		synchronized (this.running) {
			if (this.running.get()) {
				this.running.set(false);

				this.threadPool.shutdownNow();

				if (this.handler != null) { this.handler.accept(this); }
				System.out.println("Finished pool from " + Thread.currentThread().getName());
			}
		}
		return this;
	}

	/** TODO: Documentation */
	public WorkerPool shutdown() {
		if (this.threadPool != null) {
			this.threadPool.shutdown();
		}
		return this;
	}

}
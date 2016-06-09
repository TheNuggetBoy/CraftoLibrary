package de.craftolution.craftolibrary;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import javax.annotation.Nullable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 07.06.2016
 */
public class Worker implements ToStringable {

	protected final int id;
	protected final WorkerPool pool;
	protected final Runnable runnable;
	protected Consumer<Worker> consumer;

	Worker(final WorkerPool pool, final int id, final Consumer<Worker> consumer) {
		this.id = id;
		this.pool = pool;
		this.consumer = consumer;
		this.runnable = () -> {
			try { this.consumer.accept(this); }
			catch (final Exception e) {
				if (e.getMessage() != null && e.getMessage().contains("already stopped!") && e.getMessage().startsWith("The pool of worker")) { }
				else if (e instanceof InterruptedException) { System.out.println("Worker " + this.id + " interrupted."); }
				else { e.printStackTrace(); }
			}
		};
	}

	protected Worker(final WorkerPool pool, final int id, final Consumer<Worker> consumer, final boolean a) {
		this(pool, id, consumer);
	}

	/** TODO: Documentation */
	public void start(@Nullable final ExecutorService executor) {
		if (executor != null) {
			executor.execute(this.runnable);
		}
		else {
			final Thread t = new Thread(this.runnable, "WorkerThread-" + this.consumer.toString());
			t.start();
		}

	}

	public void finish() {
		this.check();
		this.pool.finish();
		System.out.println("Worker " + this.id + " called finish!");
	}

	/** TODO: Documentation */
	public WorkerPool getPool() { return this.pool; }

	/** TODO: Documentation */
	public boolean check() throws IllegalStateException {
		final boolean result = this.getPool().isRunning();
		if (!result) { System.out.println("Stopped worker " + this.id);  throw new IllegalStateException("The pool of worker " + this.id + " (" + this.consumer.toString() + ") already stopped!"); }
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.consumer == null ? 0 : this.consumer.hashCode());
		result = prime * result + this.id;
		result = prime * result + (this.pool == null ? 0 : this.pool.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Worker other = (Worker) obj;
		if (this.consumer == null) {
			if (other.consumer != null) {
				return false;
			}
		}
		else if (!this.consumer.equals(other.consumer)) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.pool == null) {
			if (other.pool != null) {
				return false;
			}
		}
		else if (!this.pool.equals(other.pool)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.buildToString()
				.with("id", this.id)
				.with("consumer", this.consumer.toString())
				.with("pool", this.pool)
				.toString();
	}

}
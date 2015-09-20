/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.concurrent.ThreadSafe;

import com.google.common.annotations.Beta;

/**
 * TODO: File description for Execution.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 25.07.2015 21:32:25
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
@Beta
@ThreadSafe
public class Routine implements ToStringable {

	public static void main(final String[] args) {
		System.out.println("Starting on " + Thread.currentThread().getName());
		Routine.go(() -> System.out.println("Hi " + Thread.currentThread().getName()))
		.after(() -> System.out.println("Hi2 " + Thread.currentThread().getName()))
		.during(ThreadType.ASYNC, () -> System.out.println("Hi3 " + Thread.currentThread().getName()))
		.after(() -> System.out.println("Hi4 " + Thread.currentThread().getName()))
		.execute();
	}

	private Optional<Routine> executeAfter;
	private Optional<Routine> executeDuring;
	private final Runnable runnable;
	private final ThreadType type;
	private final AtomicBoolean running;
	private final AtomicBoolean executed;

	private Routine(final Runnable runnable, final ThreadType type) {
		this.executeAfter = Optional.empty();
		this.executeDuring = Optional.empty();
		this.runnable = runnable;
		this.type = type;
		this.running = new AtomicBoolean(false);
		this.executed = new AtomicBoolean(false);
	}

	public Routine execute() {
		//		if (this.type.equals(ThreadType.SYNC)) {
		//			Bukkit.getScheduler().runTask(CraftoPlugin.instance(), () -> {
		//				this.deepExecute();
		//			});
		//		}
		//		else {
		Threads.go(() -> {
			this.deepExecute();
		});
		//		}
		return this;
	}

	private void deepExecute() {
		if (!this.executed.get() && !this.running.get()) {
			this.running.set(true);
			if (this.executeDuring.isPresent()) {
				this.executeDuring.get().execute();
			}
			this.runnable.run();
			this.running.set(false);
			this.executed.set(true);
			if (this.executeAfter.isPresent()) {
				this.executeAfter.get().execute();
			}
		}
	}

	public static Routine go(final Runnable r) {
		Check.notNull("The runnable cannot be null!", r);
		return new Routine(r, ThreadType.ASYNC);
	}

	public static Routine go(final ThreadType type, final Runnable r) {
		Check.notNull("The type/runnable cannot be null!", type, r);
		return new Routine(r, type);
	}

	public Routine after(final Routine routine) {
		Check.notNull("The routine cannot be null!", routine);
		if (!this.equals(routine)) { this.executeAfter = Optional.of(routine); }
		return this;
	}

	public Routine after(final Runnable r) {
		Check.notNull("The runnable cannot be null!", r);
		this.executeAfter = Optional.of(new Routine(r, ThreadType.ASYNC));
		return this.executeAfter.get();
	}

	public Routine after(final ThreadType type, final Runnable r) {
		Check.notNull("The type/runnable cannot be null!", type, r);
		this.executeAfter = Optional.of(new Routine(r, type));
		return this.executeAfter.get();
	}

	public Routine during(final Routine routine) {
		Check.notNull("The routine cannot be null!", routine);
		if (!this.equals(routine)) { this.executeDuring = Optional.of(routine); }
		return this;
	}

	public Routine during(final Runnable r) {
		Check.notNull("The runnable cannot be null!", r);
		this.executeDuring = Optional.of(new Routine(r, ThreadType.ASYNC));
		return this;
	}

	public Routine during(final ThreadType type, final Runnable r) {
		Check.notNull("The type/runnable cannot be null!", type, r);
		this.executeDuring = Optional.of(new Routine(r, ThreadType.ASYNC));
		return this;
	}

	public boolean isRunning() {
		return this.running.get();
	}

	public boolean wasExecuted() {
		return this.executed.get();
	}

	@Override
	public String toString() {
		return this.buildToString()
				.with("executed", this.executed.get())
				.with("runnable", this.runnable.toString())
				.with("type", this.type.toString())
				.toString();
	}

	enum ThreadType {
		SYNC,
		ASYNC
	}
}
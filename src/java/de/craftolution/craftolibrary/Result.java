/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.base.Supplier;

/**
 * TODO: File description for Result.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 30.08.2015 02:20:26
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class Result<T> implements Supplier<T>, java.util.function.Supplier<T> {

	@Nullable private final T object;
	@Nullable private final Throwable exception;

	private Result(final T object, final Throwable exception) {
		this.object = object;
		this.exception = exception;
	}

	/** TODO: Documentation */
	public boolean isSuccess() { return this.object != null; }

	/** TODO: Documentation */
	@Override
	public T get() throws NoSuchElementException { 
		return this.object; 
	}

	/** TODO: Documentation */
	public Optional<T> getAsOptional() {
		return Optional.ofNullable(this.object);
	}

	/** TODO: Docuemntation */
	public boolean ifSuccess(Consumer<? super T> consumer) {
		if (this.isSuccess() && consumer != null) {
			consumer.accept(this.object);
			return true;
		}
		return false;
	}

	/** TODO: Docuemntation */
	public T orElse(T other) {
		return this.isSuccess() ? this.object : other;
	}

	/** TODO: Docuemntation */
	public T orElse(Result<T> other) {
		return this.isSuccess() ? this.object : other.orNull();
	}

	/** TODO: Docuemntation */
	public T orElse(Optional<T> other) {
		return this.isSuccess() ? this.object : other.orElse(null);
	}

	/** TODO: Docuemntation */
	public T orElse(Supplier<T> other) {
		return this.isSuccess() ? this.object : other.get();
	}

	/** TODO: Docuemntation */
	public <E extends Exception> T orThrow(E e) throws E {
		if (this.isSuccess()) { return this.object; }
		else { throw e; }
	}

	/** TODO: Documentation */
	@Nullable public T orCall(Runnable runnable) {
		if (this.isSuccess()) { return this.object; }
		else { runnable.run(); return null; }
	}

	/** TODO: Documentation */
	@Nullable public T orCall(Consumer<Result<T>> consumer) {
		if (this.isSuccess()) { return this.object; }
		else { consumer.accept(this); return null; }
	}

	/** TODO: Docuemntation */
	public T orNull() {
		return this.isSuccess() ? this.object : null;
	}
	
	/** TODO: Documentation */
	public Optional<Throwable> getException() { return Optional.ofNullable(this.exception); }

	/** TODO: Documentation */
	public static <T> Result<T> of(@Nullable final T object, @Nullable final Throwable exception) {
		return new Result<T>(object, exception);
	}

	/** TODO: Documentation */
	public static <T> Result<T> of(final T object) {
		return new Result<T>(object, null);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> ofException(final Throwable exception) {
		return (Result<T>) new Result<Object>(null, exception);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> completeFailure() {
		return (Result<T>) new Result<Object>(null, null);
	}
}
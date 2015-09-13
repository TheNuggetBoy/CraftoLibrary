/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Optional;

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
public class Result<T> implements Supplier<Optional<T>>, java.util.function.Supplier<Optional<T>> {

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
	public Optional<T> get() { return Optional.ofNullable(this.object); }

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
	public static <T> Result<T> of(final Throwable exception) {
		return (Result<T>) new Result<Object>(null, exception);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> completeFailure() {
		return (Result<T>) new Result<Object>(null, null);
	}
}
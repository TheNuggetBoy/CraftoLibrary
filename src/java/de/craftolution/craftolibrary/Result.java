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

import javax.annotation.Nullable;

/**
 * TODO: File description for Result.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 30.08.2015 02:20:26
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class Result<T> implements NullableSupplier<T> {

	@Nullable private final T object;
	@Nullable private final Exception exception;

	private Result(final T object, final Exception exception) {
		this.object = object;
		this.exception = exception;
	}

	/** TODO: Documentation */
	@Override
	public T get() throws NoSuchElementException {
		if (this.object == null) { throw new NoSuchElementException("The value inside this result is null therefore inaccessible!"); }
		return this.object;
	}

	/** TODO: Documentation */
	public Optional<Exception> getException() { return Optional.ofNullable(this.exception); }

	/** TODO: Documentation */
	public static <T> Result<T> of(@Nullable final T object, @Nullable final Exception exception) {
		return new Result<T>(object, exception);
	}

	/** TODO: Documentation */
	public static <T> Result<T> of(final T object) {
		return new Result<T>(object, null);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> ofException(final Exception exception) {
		return (Result<T>) new Result<Object>(null, exception);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> completeFailure() {
		return (Result<T>) new Result<Object>(null, null);
	}
}
/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

/**
 * TODO: File description for ReturnCache.java
 *
 * @author Fear837, Pingebam
 * @param <E> - Specifies what should be stored in this cached field.
 * @version 1.0
 * @since 19.02.2015 17:30:57
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public abstract class CachedField<E> implements Supplier<E>, com.google.common.base.Supplier<E> {

	private final long lastAccess;

	@Nullable private final Duration duration;
	@Nullable private Predicate<E> predicate;

	private E value;

	/**
	 * Initializes a new {@link CachedField} with the given duration.
	 * @param duration - The duration to define after how many milliseconds the cached value should be reset.
	 * @throws IllegalArgumentException If the specified duration is {@code null}.
	 */
	public CachedField(final Duration duration) throws IllegalArgumentException {
		Check.notNull("The duration cannot be null!", duration);
		this.lastAccess = 0;
		this.duration = duration;
		this.value = this.get();
	}

	/**
	 * Initilaizes a new {@link CachedField} with the given predicate.
	 * @param predicate - The predicate to define when the cached value should be reset.
	 * @throws IllegalArgumentException If the specified predicate is {@code null}.
	 */
	public CachedField(final Predicate<E> predicate) throws IllegalArgumentException {
		Check.notNull("The predicate cannot be null!", predicate);
		this.lastAccess = 0;
		this.duration = null;
		this.predicate = predicate;
	}

	/**
	 * Initializes a new {@link CachedField} with a duration and predicate.
	 * @param duration - The predicate to define after how many milliseconds the cached value should be reset.
	 * @param predicate - The predicate to define when the cached value should be reset.
	 * @throws IllegalArgumentException If the given duration or predicate are {@code null}.
	 */
	public CachedField(final Duration duration, final Predicate<E> predicate) throws IllegalArgumentException {
		Check.notNull("The duration/predicate cannot be null!", duration, predicate);
		this.lastAccess = 0;
		this.duration = duration;
		this.predicate = predicate;
	}

	/**
	 * Sets the new value for this {@link CashedField}.
	 * @param oldValue - The old value of this field. (Could be {@code null}!)
	 * @return Return the new value for this field.
	 */
	protected abstract E set(@Nullable final E oldValue);

	/** @return Returns the value of this cached field. */
	@Override
	@Nullable public E get() {
		if (this.needsNewValue()) {
			this.value = this.set(this.value);
			synchronized (this) { this.notifyAll(); }
		}
		return this.value;
	}

	/** @retrun Returns {@code true} if this cached field needs to reset its value. */
	public boolean needsNewValue() {
		return !this.checkDuration() || !this.checkPredicate();
	}

	private boolean checkPredicate() {
		return this.predicate != null && this.predicate.apply(this.value);
	}

	private boolean checkDuration() {
		return this.duration != null && this.lastAccess + this.duration.inMillis() < System.currentTimeMillis();
	}
}
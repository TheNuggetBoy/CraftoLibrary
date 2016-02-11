/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CachedSupplier<E> implements Supplier<E>, com.google.common.base.Supplier<E> {

	private final Supplier<E> supplier;
	private final Predicate<E> predicate;
	private final Duration duration;

	private final long lastAccess = 0;
	private E element;

	public CachedSupplier(@Nonnull final Supplier<E> supplier, @Nonnull final Predicate<E> predicate, @Nonnull final Duration duration) {
		this.supplier = supplier;
		this.predicate = predicate;
		this.duration = duration;
	}

	public CachedSupplier(@Nonnull final Supplier<E> supplier, @Nonnull final Predicate<E> predicate) {
		this(supplier, predicate, null);
		Check.nonNulls("The supplier/predicate cannot be null!", supplier, this.duration);
	}

	public CachedSupplier(@Nonnull final Supplier<E> supplier, @Nonnull final Duration duration) {
		this(supplier, null, duration);
		Check.nonNulls("The supplier/duration cannot be null!", supplier, duration);
	}

	@Override
	@Nullable
	public E get() {
		if (this.requieresSupply()) {
			this.element = this.supplier.get();
		}

		return this.element;
	}

	private boolean checkDuration() { return this.duration == null || System.currentTimeMillis() - this.lastAccess > this.duration.toMillis(); }

	private boolean checkPredicate() { return this.predicate == null || this.predicate.test(this.element); }

	public boolean requieresSupply() { return this.checkDuration() && this.checkPredicate(); }

}
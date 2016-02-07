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
	
	private long lastAccess = 0;
	private E element;
	
	public CachedSupplier(@Nonnull Supplier<E> supplier, @Nonnull Predicate<E> predicate, @Nonnull Duration duration) {
		this.supplier = supplier;
		this.predicate = predicate;
		this.duration = duration;
	}
	
	public CachedSupplier(@Nonnull Supplier<E> supplier, @Nonnull Predicate<E> predicate) {
		this(supplier, predicate, null);
		Check.notNull("The supplier/predicate cannot be null!", supplier, duration);
	}
	
	public CachedSupplier(@Nonnull Supplier<E> supplier, @Nonnull Duration duration) {
		this(supplier, null, duration);
		Check.notNull("The supplier/duration cannot be null!", supplier, duration);
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
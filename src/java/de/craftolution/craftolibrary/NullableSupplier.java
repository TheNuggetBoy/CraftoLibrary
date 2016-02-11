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
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.base.Function;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 02.10.2015
 */
@FunctionalInterface
public interface NullableSupplier<T> extends Supplier<T>, com.google.common.base.Supplier<T> {

	/** TODO: Documentation */
	@Override T get() throws NoSuchElementException;

	/** TODO: Documentation */
	default Optional<T> getAsOptional() {
		return Optional.ofNullable(this.get());
	}

	/** TODO: Documentation */
	default boolean isPresent() {
		return this.get() != null;
	}

	/** TODO: Documentation */
	default boolean ifPresent(final Consumer<T> consumer) {
		if (this.isPresent()) {
			consumer.accept(this.get());
			return true;
		}
		return false;
	}

	/** TODO: Documentation */
	default boolean ifPresent(final Runnable runnable) {
		if (this.isPresent()) {
			runnable.run();
			return true;
		}
		return false;
	}

	/** TODO: Documentation */
	default boolean ifPresentSupply(final Consumer<NullableSupplier<T>> consumer) {
		if (this.isPresent()) {
			consumer.accept(this);
			return true;
		}
		return false;
	}

	/** TODO: Documentation */
	default T orElse(final T other) {
		return this.isPresent() ? this.get() : other;
	}

	/** TODO: Documentation */
	default T orElse(final Supplier<T> other) {
		return this.isPresent() ? this.get() : other.get();
	}

	/** TODO: Documentation */
	default T orElse(final Optional<T> other) {
		return this.isPresent() ? this.get() : other.orElse(null);
	}

	/** TODO: Documentation */
	default <E extends Throwable> T orThrow(final E exception) throws E {
		if (this.isPresent()) { return this.get(); }
		else { throw exception; }
	}

	/** TODO: Documentation */
	default <E extends Throwable> T orThrow(final Supplier<E> exceptionSupplier) throws E {
		if (this.isPresent()) { return this.get(); }
		else { throw exceptionSupplier.get(); }
	}

	/** TODO: Documentation */
	@Nullable default T orNull() {
		return this.isPresent() ? this.get() : null;
	}

	/** TODO: Documentation */
	default <U> Optional<U> transform(final Function<? super T, ? extends U> mapper) {
		if (this.isPresent()) {
			return Optional.ofNullable(mapper.apply(this.get()));
		}
		return Optional.empty();
	}

}
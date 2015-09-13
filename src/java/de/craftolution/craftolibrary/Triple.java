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

/**
 * TODO: File description for Triple.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 15.04.2015 22:14:29
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class Triple<F, S, T> extends Pair<F, S> {

	private static final Triple<?, ?, ?> EMPTY = Triple.of(null, null, null);

	@Nullable private final T third;

	protected Triple(final F first, final S second) {
		this(first, second, null);
	}

	protected Triple(final F first, final S second, final T third) {
		super(first, second);
		this.third = third;
	}

	/** @return Returns the third object stored in this triple. */
	public T getThird() { return this.third; }

	/** @return Returns the third object as an {@link Optional}. */
	public Optional<T> getThirdAsOptional() { return Optional.<T>ofNullable(this.getThird()); }

	/** @return Returns {@code true}, if the third object is not {@code null}. */
	public boolean isThirdPresent() { return this.getThird() != null; }

	/** @return Returns {@code true}, if all objects are {@code null}. */
	@Override
	public boolean allNull() { return this.getFirst() == null && this.getSecond() == null && this.getThird() == null; }

	/** @return Returns {@code true}, if all objects are {@code present}. */
	@Override
	public boolean allPresent() { return this.getFirst() != null && this.getSecond() != null && this.getThird() != null; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.getFirst() == null ? 0 : this.getFirst().hashCode());
		result = prime * result + (this.getSecond() == null ? 0 : this.getSecond().hashCode());
		result = prime * result + (this.getThird() == null ? 0 : this.getThird().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Triple)) { return false; }
		final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
		if (this.getFirst() == null) {
			if (other.getFirst() != null) { return false; }
		}
		else if (!this.getFirst().equals(other.getFirst())) { return false; }
		if (this.getSecond() == null) {
			if (other.getSecond() != null) { return false; }
		}
		else if (!this.getSecond().equals(other.getSecond())) { return false; }
		if (this.getThird() == null) {
			if (other.getThird() != null) { return false; }
		}
		else if (!this.getThird().equals(other.getThird())) { return false; }
		return true;
	}

	/** TODO: Documentation */
	public static <F, S, T> Triple<F, S, T> of(@Nullable final Triple<F, S, T> triple) {
		return triple != null ? new Triple<F, S, T>(triple.getFirst(), triple.getSecond(), triple.getThird()) : new Triple<F, S, T>(null, null, null);
	}

	/** TODO: Documentation */
	public static <F, S, T> Triple<F, S, T> of(@Nullable final F first, @Nullable final S second, @Nullable final T third) {
		return new Triple<F, S, T>(first, second, third);
	}

	/** TODO: Documentation */
	public static <F, S, T> Triple<F, S, T> of(@Nullable final F first, @Nullable final Pair<S, T> secondAndThird) {
		return new Triple<F, S, T>(first, secondAndThird.getFirst(), secondAndThird.getSecond());
	}

	/** TODO: Documentation */
	public static <F, S, T> Triple<F, S, T> of(@Nullable final Pair<F, S> firstAndSecond, @Nullable final T third) {
		return new Triple<F, S, T>( firstAndSecond.getFirst(), firstAndSecond.getSecond(), third);
	}

	/** @return Returns an empty immutable triple. */
	@SuppressWarnings("unchecked")
	public static <F, S, T> Triple<F, S, T> emptyTriple() {
		return (Triple<F, S, T>) Triple.EMPTY;
	}
}
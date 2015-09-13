/*
 * Copyright (C) 2014 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

/**
 * A simple pair class that is able to contain two objects.
 *
 * @param <F> - The first element
 * @param <S> - The second element
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 16.12.2014 20:42:23
 * @see <a href="https://github.com/Craftolution">CraftolutionDE on Github</a>
 */
public class Pair<F, S> implements Comparable<Pair<?, ?>>{

	private static final Pair<?, ?> EMPTY = Pair.of(null, null);

	@Nullable private final F first;
	@Nullable private final S second;

	protected Pair(final F first, final S second) {
		this.first = first;
		this.second = second;
	}

	/** @return Returns the first object stored in this pair. */
	public F getFirst() { return this.first; }

	/** @return Returns the second object stored in this pair. */
	public S getSecond() { return this.second; }

	/** @return Returns the first object as an {@link Optional}. */
	public Optional<F> getFirstAsOptional() { return Optional.ofNullable(this.getFirst()); }

	/** @return Returns the second object as an {@link Optional}. */
	public Optional<S> getSecondAsOptional() { return Optional.ofNullable(this.getSecond()); }

	/** @return Returns {@code true}, if the first object is not {@code null}. */
	public boolean isFirstPresent() { return this.getFirst() != null; }

	/** @return Returns {@code true}, if the second object is not {@code null}. */
	public boolean isSecondPresent() { return this.getSecond() != null; }

	/** @return Returns {@code true}, if both objects are {@code null}. */
	public boolean allNull() { return this.getFirst() == null && this.getSecond() == null; }

	/** @return Returns {@code true}, if both objects are {@code present}. */
	public boolean allPresent() { return this.getFirst() != null && this.getSecond() != null; }

	/**
	 * Transforms the first and second value with the given function.
	 * If the instances are all present, it is transformed with the given {@link Function}; otherwise,
	 * {@link Optional#absent()} is returned.
	 * @param function - The function that transforms the pair.
	 * @return Returns the result of the function.
	 */
	public Optional<Pair<F, S>> transform(final BiFunction<F, S, Pair<F, S>> function) {
		// This method is unique to Pair as there is no TriFunction for triples.
		if (this.allPresent()) {
			return Optional.ofNullable(function.apply(this.getFirst(), this.getSecond()));
		}
		return Optional.empty();
	}

	@Override
	public int compareTo(final Pair<?, ?> other) {
		if (other.allPresent()) {
			if (other.isFirstPresent()) {
				if (other.isSecondPresent()) {
					if (this.isFirstPresent()) {
						if (this.isSecondPresent()) {
							return 0;
						} else { return -1; }
					} else { return -1; }
				} else { return 1; }
			} else { return 1; }
		} else { return 1; }
	}

	@Override
	public int hashCode() {
		return 31 * (this.getFirst() != null ? this.getFirst().hashCode() : 0) + (this.getSecond() != null ? this.getSecond().hashCode() : 0);
	}

	@Override
	public boolean equals(final Object object) {
		if (object != null && object instanceof Pair<?, ?>) {
			final Pair<?, ?> other = (Pair<?, ?>)object;
			if (other.equals(this)) { return true; }
			else {
				if (other.allPresent() && other.getFirst().equals(this.getFirst()) && other.getSecond().equals(this.getSecond())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * TODO: Documentation
	 * @param pair
	 * @return
	 */
	public static <F, S> Pair<F, S> of(@Nullable final Pair<F, S> pair) {
		return pair != null ? new Pair<F, S>(pair.getFirst(), pair.getSecond()) : new Pair<F, S>(null, null);
	}

	/**
	 * Creates a new pair of the two specified objects.
	 * @param first - The first object.
	 * @param second - The second object.
	 * @return Returns a new pair.
	 */
	public static <F, S> Pair<F, S> of(@Nullable final F first, @Nullable final S second) {
		return new Pair<F, S>(first, second);
	}

	/** @return Returns an empty immutable pair. */
	@SuppressWarnings("unchecked")
	public static <F, S> Pair<F, S> emptyPair() { return (Pair<F, S>) Pair.EMPTY; }
}
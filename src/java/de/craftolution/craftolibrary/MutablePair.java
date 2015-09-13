/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import javax.annotation.Nullable;

/**
 * TODO: File description for ChangeablePair.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 19.02.2015 18:03:01
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class MutablePair<F, S> extends Pair<F, S> {

	@Nullable private F first;
	@Nullable private S second;

	protected MutablePair(final F first, final S second) {
		super(null, null);
		this.first = first;
		this.second = second;
	}

	@Override
	public F getFirst() { return this.first; }

	@Override
	public S getSecond() { return this.second; }

	/**
	 * Sets the first value in this pair.
	 * @param first - The first value
	 * @return Returns the old first value.
	 */
	public F setFirst(@Nullable final F first) {
		final F oldFirst = this.getFirst();
		this.first = first;
		return oldFirst;
	}

	/**
	 * Sets the second value in this pair.
	 * @param second - The second value
	 * @return Returns the old second value.
	 */
	public S setSecond(@Nullable final S second) {
		final S oldSecond = this.getSecond();
		this.second = second;
		return oldSecond;
	}

	/** TODO: Documentation */
	public Pair<F, S> set(@Nullable final Pair<F, S> newValues) {
		return newValues != null ? this.set(newValues.getFirst(), newValues.getSecond()) : this.set(null, null);
	}

	/** TODO: Documentation */
	public Pair<F, S> set(@Nullable final F firstValue, @Nullable final S secondValue) {
		final F oldFirst = this.getFirst();
		final S oldSecond = this.getSecond();
		this.setFirst(firstValue);
		this.setSecond(secondValue);
		return Pair.of(oldFirst, oldSecond);
	}

	/**
	 * TODO: Documentation
	 * @param pair
	 * @return
	 */
	public static <F, S> MutablePair<F, S> of(@Nullable final Pair<F, S> pair) {
		return pair != null ? new MutablePair<F, S>(pair.getFirst(), pair.getSecond()) : new MutablePair<F, S>(null, null);
	}

	/** TODO: Documentation */
	public static <F, S> MutablePair<F, S> of(@Nullable final F first, @Nullable final S second) {
		return new MutablePair<F, S>(first, second);
	}
}
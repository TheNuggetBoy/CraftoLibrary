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
 * TODO: File description for MutableTriple.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 19.08.2015 01:48:48
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public class MutableTriple<F, S, T> extends Triple<F, S, T> {

	@Nullable private F first;
	@Nullable private S second;
	@Nullable private T third;

	/**
	 * @param first
	 * @param second
	 * @param third
	 */
	protected MutableTriple(final F first, final S second, final T third) {
		super(null, null, null);
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Override
	public F getFirst() { return this.first; }

	@Override
	public S getSecond() { return this.second; }

	@Override
	public T getThird() { return this.third; }

	/**
	 * Sets the first value in this triple.
	 * @param first - The first value
	 * @return Returns the old first value.
	 */
	public F setFirst(@Nullable final F first) {
		final F oldFirst = this.getFirst();
		this.first = first;
		return oldFirst;
	}

	/**
	 * Sets the second value in this triple.
	 * @param second - The second value
	 * @return Returns the old second value.
	 */
	public S setSecond(@Nullable final S second) {
		final S oldSecond = this.getSecond();
		this.second = second;
		return oldSecond;
	}

	/**
	 * Sets the third value in this triple.
	 * @param third - The third value
	 * @return Returns the old third value.
	 */
	public T setThird(@Nullable final T third) {
		final T oldThird = this.getThird();
		this.third = third;
		return oldThird;
	}

	/** TODO: Documentation */
	public Triple<F, S, T> set(@Nullable final Triple<F, S, T> newValues) {
		return newValues != null ? this.set(newValues.getFirst(), newValues.getSecond(), newValues.getThird()) : this.set(null, null, null);
	}

	/** TODO: Documentation */
	public Triple<F, S, T> set(@Nullable final F firstValue, @Nullable final S secondValue, @Nullable final T thirdValue) {
		final F oldFirst = this.getFirst();
		final S oldSecond = this.getSecond();
		final T oldThird = this.getThird();
		this.setFirst(firstValue);
		this.setSecond(secondValue);
		this.setThird(thirdValue);
		return Triple.of(oldFirst, oldSecond, oldThird);
	}

	/** TODO: Documentation */
	public static <F, S, T> MutableTriple<F, S, T> of(@Nullable final Triple<F, S, T> triple) {
		return triple != null ? new MutableTriple<F, S, T>(triple.getFirst(), triple.getSecond(), triple.getThird()) : new MutableTriple<F, S, T>(null, null, null);
	}

	/** TODO: Documentation */
	public static <F, S, T> MutableTriple<F, S, T> of(@Nullable final F first, @Nullable final S second, @Nullable final T third) {
		return new MutableTriple<F, S, T>(first, second, third);
	}

	/** TODO: Documentation */
	public static <F, S, T> MutableTriple<F, S, T> of(@Nullable final F first, @Nullable final Pair<S, T> secondAndThird) {
		return new MutableTriple<F, S, T>(first, secondAndThird.getFirst(), secondAndThird.getSecond());
	}

	/** TODO: Documentation */
	public static <F, S, T> MutableTriple<F, S, T> of(@Nullable final Pair<F, S> firstAndSecond, @Nullable final T third) {
		return new MutableTriple<F, S, T>( firstAndSecond.getFirst(), firstAndSecond.getSecond(), third);
	}
}
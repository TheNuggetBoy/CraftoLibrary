/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nullable;

/**
 * Basically represents an array of {@link T} (therefore resizing a tuple is not possible) elements but offers some
 * neat methods for easier practice.
 *
 * To access an element or to modify one the {@link #at(int)} & {@link #at(int, Object)} methods are used.
 * This class offers some conversion support from collection/array/list/set etc. to tuple and vice versa.
 *
 *
 * @author Fear837
 * @since 08.02.2016
 */
public class Tuple<T> extends ImmutableTuple<T> {

	private static final long serialVersionUID = 4000205510737837777L;

	private static final Tuple<?> EMPTY = new Tuple<Object>(new Object[]{});

	Tuple(final T[] tokens) { 
		super(tokens);
	}

	/**
	 * Removes all of the elements from this array by setting them to {@code null}.
	 * The array will be empty after this call returns.
	 */
	public void clear() {
		for (int i = 0; i < this.tokens.length; i++) {
			this.tokens[i] = null;
		}
		this.needsNewEmptyCalculation = false;
		this.empty = true;
	}

	/** @return Creates an immutable tuple with the same elements as this one. */
	@SuppressWarnings("unchecked")
	public ImmutableTuple<T> toImmutable() {
		final Object[] newArray = new Object[this.tokens.length];
		System.arraycopy(this.tokens, 0, newArray, 0, this.tokens.length);
		return new ImmutableTuple<T>((T[]) newArray);
	}

	/**
	 * Extracts an array from the specified source array, beginning at the specified position, to the specified position of the destination array.
	 * A subsequence of array components are copied from the source array referenced by {@code from} to this tuple.
	 * The number of components copied is equal to the {@code length} argument. The components at positions {@code sourceIndex} trough {@code sourceIndex+length-1} in the
	 * source array are copied into positions {@code targetIndex} through {@code targetIndex + length - 1}, respectively, of the destination array.
	 *
	 * @param from - The tokens array to copy the elements from
	 * @param sourceIndex - The starting index in the from array
	 * @param targetIndex - The starting index of this array
	 * @param length - The number of elements to be copied
	 */
	public void extract(final Tuple<T> from, final int sourceIndex, final int targetIndex, final int length) {
		for (int i = sourceIndex; i < length; i++) {
			this.tokens[targetIndex + i] = from.at(sourceIndex + i);
		}
	}

	/**
	 * Extracts an array from the specified source array, beginning at the specified position, to the specified position of the destination array.
	 * A subsequence of array components are copied from the source array referenced by {@code from} to this tuple.
	 * The number of components copied is equal to the {@code length} argument. The components at positions {@code sourceIndex} trough {@code sourceIndex+length-1} in the
	 * source array are copied into positions {@code targetIndex} through {@code targetIndex + length - 1}, respectively, of the destination array.
	 *
	 * @param from - The tokens array to copy the elements from
	 * @param sourceIndex - The starting index in the from array
	 * @param targetIndex - The starting index of this array
	 * @param length - The number of elements to be copied
	 */
	public void extract(final T[] from, final int sourceIndex, final int targetIndex, final int length) {
		for (int i = sourceIndex; i < length; i++) {
			this.tokens[targetIndex + i] = from[sourceIndex + i];
		}
	}

	/**
	 * Accesses the element at the specified index in this array and provides a {@code newValue} argument
	 * for placing a new element at that index.
	 *
	 * @param index - The index to access
	 * @param newValue - The new element to place at that index
	 * @return Returns {@code newValue}
	 */
	@Nullable public T at(final int index, @Nullable final T newValue) {
		this.tokens[index] = newValue;

		if (newValue != null) { this.empty = false; }
		else { this.needsNewEmptyCalculation = true; }

		return newValue;
	}

	/**
	 * Creates an array with all of the specified elements.
	 *
	 * @param tokens The elements
	 * @return Returns a new array containing the {@code tokens}.
	 */
	@SafeVarargs
	public static <T> Tuple<T> of(final T... tokens) {
		return new Tuple<T>(tokens);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> ofDefault(final int length, final T defaultElement) {
		final Object[] array = new Object[length];
		for (int i = 0; i < length; i++) { array[i] = defaultElement; }
		return new Tuple<T>((T[]) array);
	}

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> ofProvider(final int length, final Function<Integer, T> elementProvider) {
		final Object[] array = new Object[length];
		for (int i = 0; i < length; i++) { array[i] = elementProvider.apply(i); }
		return new Tuple<T>((T[]) array);
	}

	/**
	 * Creates a tuple containg both of the given tuples This allows for tuples to be concatenated.
	 *
	 * @param tuple - The first tuple
	 * @param otherTuple - the second tuple
	 * @return Returns a new {@link Tuple} containing all the elements from the first and second tuple.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> concatenate(final Tuple<T> tuple, final Tuple<T> otherTuple) {
		final Object[] newArray = new Object[tuple.length() + otherTuple.length()];
		System.arraycopy(tuple.tokens, 0, newArray, 0, tuple.length());
		System.arraycopy(otherTuple.tokens, 0, newArray, tuple.length(), otherTuple.length());
		return new Tuple<T>((T[]) newArray);
	}

	/**
	 * Creates a tuple containg the given {@code array} and also containing all the
	 * elements specified in {@code appendedTokens}. This allows for arrays to be concatenated.
	 *
	 * @param array
	 * @param appendedTokens
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> concatenate(final Tuple<T> tuple, final T... appendedTokens) {
		final Object[] newArray = new Object[tuple.length() + appendedTokens.length];
		System.arraycopy(tuple.tokens, 0, newArray, 0, tuple.length());
		System.arraycopy(appendedTokens, 0, newArray, tuple.length(), appendedTokens.length);
		return new Tuple<T>((T[]) newArray);
	}

	/**
	 * Creates a tuple containg the given {@code array} and also containing all the
	 * elements specified in {@code appendedTokens}. This allows for arrays to be concatenated.
	 *
	 * @param array
	 * @param appendedTokens
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> concatenate(final T[] array, final T... appendedTokens) {
		final Object[] newArray = new Object[array.length + appendedTokens.length];
		System.arraycopy(array, 0, newArray, 0, array.length);
		System.arraycopy(appendedTokens, 0, newArray, array.length, appendedTokens.length);
		return new Tuple<T>((T[]) newArray);
	}

	/**
	 * Creates an array with all of the elements that {@code tokens} contains.
	 *
	 * @param tokens - The collection to copy the elements from.
	 * @return Returns a new array
	 */
	public static <T> Tuple<T> of(final Collection<T> tokens) {
		@SuppressWarnings("unchecked")
		final
		T[] tokensArray = (T[]) tokens.toArray();
		return new Tuple<T>(tokensArray);
	}

	/** @return Basically returns a copy of the specified array containing all the elements that {@code original} has. */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> of(final Tuple<T> original) {
		final Object[] tokens = new Object[original.tokens.length];
		System.arraycopy(original.tokens, 0, tokens, 0, original.tokens.length);
		return new Tuple<T>((T[]) tokens);
	}

	/** @return Returns an empty array. */
	@SuppressWarnings("unchecked")
	public static <T> Tuple<T> empty() { return (Tuple<T>) Tuple.EMPTY; }

}
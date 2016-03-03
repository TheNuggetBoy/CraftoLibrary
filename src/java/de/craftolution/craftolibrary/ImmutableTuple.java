package de.craftolution.craftolibrary;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 01.03.2016
 */
public class ImmutableTuple<T> implements Iterable<T> {

	protected final T[] tokens;
	protected boolean empty;
	protected boolean needsNewEmptyCalculation;

	ImmutableTuple(final T[] tokens) { this.tokens = tokens; }

	/**
	 * Accesses the element at the specified index in this array.
	 * <p>Beware that the element at the specified position could be {@code null}.</p>
	 *
	 * @param index - The index to access
	 * @return Returns the element, or {@code null} if the element at that position was in fact {@code null}.
	 */
	@Nullable public T at(final int index) {
		return this.tokens[index];
	}

	/** @return Returns a new tokens array with the exact same elements of this one, but in reversed order. */
	@SuppressWarnings("unchecked")
	public Tuple<T> reverse() {
		final Object[] reversedTokens = new Object[this.tokens.length];
		for (int i = 0; i < reversedTokens.length; i++) {
			reversedTokens[i] = this.tokens[this.tokens.length - i - 1];
		}
		return Tuple.of((T[]) reversedTokens);
	}

	/**
	 * Returns an array that applies the {@code transformer} to each element of this array.
	 *
	 * @param transformer - The transformer that accepts elements of this array.
	 * @return The newly transformed array.
	 */
	@SuppressWarnings("unchecked")
	public <NewType> Tuple<NewType> transform(final Function<T, NewType> transformer) {
		final Object[] transformedTokens = new Object[this.tokens.length];
		for (int i = 0; i < this.tokens.length; i++) {
			transformedTokens[i] = transformer.apply(this.tokens[i]);
		}
		return Tuple.of((NewType[]) transformedTokens);
	}

	/**
	 * Cuts out a slice of elements whose index is between {@code fromIndex (inclusive)}, and {@code toIndex (exclusive)}.
	 * @param fromIndex - The beginning index (inclusive)
	 * @param toIndex - The ending index (exlusive)
	 * @return Returns a new {@link Tuple} instance containing the elements.
	 */
	@SuppressWarnings("unchecked")
	public Tuple<T> slice(final int fromIndex, final int toIndex) { // 0 1 2 3 4 5 6 7 8 9 10
		final Object[] slicedTokens = new Object[toIndex - fromIndex];
		for (int i = 0; i < slicedTokens.length; i++) {
			slicedTokens[i] = this.tokens[i + fromIndex];
		}
		return Tuple.of((T[]) slicedTokens);
	}

	/** @return Returns {@code true} if this array only contains {@code null}s. */
	public boolean isEmpty() {
		if (this.needsNewEmptyCalculation) {
			this.needsNewEmptyCalculation = false;

			for (final T t : this.tokens) {
				if (t != null) { this.empty = false; return false; }
			}

			this.empty = true;
		}
		return this.empty;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in this array, or {@code -1} if this array
	 * does not contain the element. More formally, returns the lowest index {@code i} such that {@code (object==null ? at(i)==null : object.equals(at(i)))}, or {@code -1}
	 * if there is no such index.
	 *
	 * @param object - The element to search for
	 * @return Returns the index of the first occurrence of the specified element in this array, or {@code -1} if this does not contain the element.
	 */
	public int indexOf(final T object) {
		for (int i = 0; i < this.tokens.length; i++) {
			final T element = this.tokens[i];
			if (object == null ? element == null : element.equals(object)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified element in this array, or {@code -1} if this list does not contain the element.
	 * More formally, returns the highest index {@code i} such that {@code (object==null ? at(i)==null : object.equals(at(i)))}, or {@code -1}
	 * if there is no such index.
	 *
	 * @param object - The element to search for
	 * @return Returns the index of the last occurrence of the specified element in this list, or {@code -1} if this array does not contain the element.
	 */
	public int lastIndexOf(final T object) {
		for (int i = this.tokens.length - 1; i >= 0; i--) {
			final T element = this.tokens[i];
			if (object == null ? element == null : element.equals(object)) {
				return i;
			}
		}

		return -1;
	}

	/** @return Returns how many elements this array contains. */
	public int length() { return this.tokens.length; }

	/** @return Returns how many elements in this array are <b>not</b> {@code null}. */
	public int count() {
		int count = 0;
		for (final T element : this.tokens) {
			if (element != null) { count++; }
		}
		return count;
	}

	/** @return Returns a sequential {@link Stream} with this array as its source. */
	public Stream<T> stream() { return Arrays.stream(this.tokens); }

	/** @return Returns an equivalent stream that is parallel. */
	public Stream<T> parallelStream() { return this.stream().parallel(); }

	/** @return Returns a <b>copy</b> of the. */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		Object[] newArray = new Object[this.tokens.length];
		System.arraycopy(this.tokens, 0, newArray, 0, tokens.length);
		return (T[]) newArray;
	}

	/** @return Returns a new set containing all of the elements in this array. */
	public Set<T> toSet() { return Sets.newHashSet(this.tokens); }

	/** @return Returns a new list containing all of the elements in this array. */
	public List<T> toList() { return Lists.newArrayList(this.tokens); }

	/**
	 * Performs the given action for each element of this array until all elements have been processed or the action throws an exception.
	 *
	 * @param action - The action to be performed for each element
	 */
	@Override
	public void forEach(final Consumer<? super T> action) {
		Check.notNull(action, "The action cannot be null!");
		for (final T element : this.tokens) { action.accept(element); }
	}

	/**
	 * Performs the given action for each element of this array, if the element is <b>not</b>{@code null}, until all elements have been processed or the action throws an exception.
	 *
	 * @param action - The action to be performed for each nonnull element.
	 */
	public void forEachNotNull(final Consumer<? super T> action) {
		for (final T element : this.tokens) {
			if (element != null) { action.accept(element); }
		}
	}

	/**
	 * Checks whether or not this tuple contains all of the given elements in the same order as they're in the list.
	 * <p>Keep in mind that, if one of the elements is {@code null} it'll equal to an {@code null} element in this tuple, so
	 * {@code null}s are allowed.</p>
	 *
	 * @param elements - The elements to check
	 * @return Returns {@code true} if this tuple contains all the elements that the list contains in the same order as they are contained in the list.
	 */
	public boolean containsInOrder(final List<T> elements) {
		if (elements == null) { return false; }
		if (elements.size() > this.length()) { return false; }
		if (elements.size() == 0) { return false; }

		final T firstElement = elements.get(0);

		for (int i = 0; i < this.tokens.length; i++) {
			T currentElement = this.tokens[i];

			if (firstElement == null ? currentElement == null : currentElement.equals(firstElement)) {
				for (int a = 0; a < elements.size(); a++) {
					final T selectedElement = elements.get(a);
					currentElement = this.tokens[i + a];

					if (selectedElement == null ? currentElement != null : !currentElement.equals(selectedElement)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks whether nor not this tuple contains all of the given elements in the same order as they're in the given tuple.
	 * <p>Keep in mind that, if one of the elements is {@code null} it'll equal to an {@code null} element in this tuple, so
	 * {@code null}s are allowed.</p>
	 *
	 * @param elements - The elements to check
	 * @return Returns {@code true} if this tuple contains all the elements that the list contains in the same order as they are contained in the list.
	 */
	public boolean containsInOrder(final Tuple<T> elements) { return this.containsInOrder(elements.tokens); }

	/**
	 * Checks whether nor not this tuple contains all of the given elements in the same order as they're in the given array.
	 * <p>Keep in mind that, if one of the elements is {@code null} it'll equal to an {@code null} element in this tuple, so
	 * {@code null}s are allowed.</p>
	 *
	 * @param elements - The elements to check
	 * @return Returns {@code true} if this tuple contains all the elements that the list contains in the same order as they are contained in the array.
	 */
	public boolean containsInOrder(final T[] elements) {
		if (elements == null) { return false; }
		if (elements.length > this.length()) { return false; }
		if (elements.length == 0) { return false; }

		final T firstElement = elements[0];

		for (int i = 0; i < this.tokens.length; i++) {
			T currentElement = this.tokens[i];

			if (firstElement == null ? currentElement == null : currentElement.equals(firstElement)) {
				for (int a = 0; a < elements.length; a++) {
					final T selectedElement = elements[a];
					currentElement = this.tokens[i + a];

					if (selectedElement == null ? currentElement != null : !currentElement.equals(selectedElement)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks whether or not this tuple contains at least one of the elements given in the iterable.
	 *
	 * @param elements - The elements to search for
	 * @return Returns {@code true} if at least one element from the {@code elements} iterable was found.
	 */
	public boolean containsAny(final Iterable<T> elements) {
		if (elements == null) { return false; }

		for (final T currentElement : elements) {
			for (int a = 0; a < this.tokens.length; a++) {
				if (currentElement == null ? this.tokens[a] == null : this.tokens[a].equals(currentElement)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks whether or not this tuple contains at least one of the elements given in the tuples.
	 *
	 * @param elements - The elements to search for
	 * @return Returns {@code true} if at least one element from the {@code elements} tuple was found.
	 */
	public boolean containsAny(final Tuple<T> elements) { return this.containsAny(elements.tokens); }

	/**
	 * Checks whether or not this tuple contains at least one of the elements given in the array.
	 *
	 * @param elements - The elements to search
	 * @return Returns {@code true} if at least one element from the {@code elements} array was found.
	 */
	public boolean containsAny(@Nullable final T[] elements) {
		if (elements == null) { return false; }
		if (elements.length > this.length()) { return false; }
		if (elements.length == 0) { return false; }

		for (int i = 0; i < elements.length; i++) {
			final T currentElement = elements[i];

			for (int a = 0; a < this.tokens.length; a++) {
				if (currentElement == null ? this.tokens[a] == null : this.tokens[a].equals(currentElement)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks whether or not this tuple contains all of the elements given, while the order
	 * doesn't matter.
	 *
	 * @param elements - The elements to search
	 * @return Returns {@code true} if this tuple contains all of the specified elements.
	 */
	public boolean containsAll(final Iterable<T> elements) {
		if (elements == null) { return false; }

		for (final T currentElement : elements) {
			boolean found = false;

			inner: for (int a = 0; a < this.tokens.length; a++) {
				if (currentElement == null ? this.tokens[a] == null : this.tokens[a].equals(currentElement)) {
					found = true;
					break inner;
				}
			}

			if (!found) { return false; }
		}

		for (@SuppressWarnings("unused") final T firstElement : elements) { return true; } // At least one element has to be in elements to return true;
		return false;
	}

	/**
	 * Checks whether or not this tuple contains all of the elements given, while the order
	 * doesn't matter.
	 *
	 * @param elements - The elements to search
	 * @return Returns {@code true} if this tuple contains all of the specified elements.
	 */
	public boolean containsAll(final Tuple<T> elements) { return this.containsAll(elements.tokens); }

	/**
	 * Checks whether or not this tuple contains all of the elements given, while the order
	 * doesn't matter.
	 *
	 * @param elements - The elements to search
	 * @return Returns {@code true} if this tuple contains all of the specified elements.
	 */
	public boolean containsAll(@Nullable final T[] elements) {
		if (elements == null) { return false; }
		if (elements.length > this.length()) { return false; }
		if (elements.length == 0) { return false; }

		for (int i = 0; i < elements.length; i++) {
			final T currentElement = elements[i];
			boolean found = false;

			inner: for (int a = 0; a < this.tokens.length; a++) {
				if (currentElement == null ? this.tokens[a] == null : this.tokens[a].equals(currentElement)) {
					found = true;
					break inner;
				}
			}

			if (!found) { return false; }
		}

		return true;
	}

	/** @return Returns {@code true} if this array contains the specified {@code object}. */
	public boolean contains(final T object) {
		if (object == null) { return false; }
		for (final T element : this.tokens) { if (element != null && object.equals(element)) { return true; } }
		return false;
	}

	@Override
	public Iterator<T> iterator() { return new TupleIterator<T>(this); }


}
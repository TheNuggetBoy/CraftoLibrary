/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBuilder<T> implements Builder<List<T>> {

	private final List<T> list = new ArrayList<>();

	private ListBuilder() { }

	/** TODO: Documentation */
	@Override
	public List<T> build() { return this.list; }

	/** TODO: Documentation */
	public ListBuilder<T> add(final T element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		this.list.add(element);
		return this;
	}

	/** TODO: Documentation */
	public ListBuilder<T> addAll(final Collection<? extends T> elements) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		this.list.addAll(elements);
		return this;
	}

	/** TODO: Documentation */
	public ListBuilder<T> addAll(final T[] elements) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		for (final T element : elements) {
			this.list.add(element);
		}
		return this;
	}

	/** TODO: Documentation */
	public ListBuilder<T> addAll(final Tuple<T> elements) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		for (final T element : elements) {
			this.list.add(element);
		}
		return this;
	}

	/** TODO: Documentation */
	public static <T> ListBuilder<T> create(final T element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		return new ListBuilder<T>().add(element);
	}

}
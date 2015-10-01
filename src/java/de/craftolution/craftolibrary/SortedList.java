/*
 * Copyright (C) 2014 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;

/**
 * TODO: File description.
 *
 * @param <E> - The object type to store in this list.
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 26.10.2014
 * @see <a href="https://github.com/Craftolution">CraftolutionDE on Github</a>
 */
public class SortedList<E> extends AbstractList<E> {

	private final ArrayList<E> internalList;

	@Nullable
	private final Comparator<E> comparator;

	/** TODO: Documentation */
	public SortedList() {
		this.internalList = new ArrayList<E>();
		this.comparator = null;
	}

	/** TODO: Documentation */
	public SortedList(final Collection<? extends E> collection) {
		this.internalList = new ArrayList<E>(collection);
		this.comparator = null;
	}

	/**
	 * TODO: Documentation
	 * @param initialCapacity
	 */
	public SortedList(final int initialCapacity) {
		this.internalList = new ArrayList<E>(initialCapacity);
		this.comparator = null;
	}

	/**
	 * TODO: Documentation
	 * @param comparator
	 */
	public SortedList(final Comparator<E> comparator) {
		this.internalList = new ArrayList<E>();
		this.comparator = comparator;
	}

	/**
	 * TODO: Documentation
	 * @param initialCapacity
	 * @param comparator
	 */
	public SortedList(final int initialCapacity, final Comparator<E> comparator) {
		this.internalList = new ArrayList<E>(initialCapacity);
		this.comparator = comparator;
	}

	/**
	 * TODO: Documentation
	 * @param initialCapacity
	 * @param comparator
	 */
	public SortedList(final Collection<? extends E> collection, final Comparator<E> comparator) {
		this.internalList = new ArrayList<E>(collection);
		this.comparator = comparator;
	}

	@Override
	public void add(final int position, final E e) { // Note that add(E e) in AbstractList is calling this one
		this.internalList.add(position, e);
		Collections.sort(this.internalList, this.comparator);
	}

	/** TODO: Documentation */
	public void addWithoutSort(final int position, final E e) {
		this.internalList.add(position, e);
	}

	/** TODO: Documentation */
	public void addWithoutSort(final E e) {
		this.internalList.add(e);
	}

	/** TODO: Documentation */
	public void sort() {
		Collections.sort(this.internalList, this.comparator);
	}

	@Override
	public E set(final int index, final E element) {
		final E e = this.internalList.set(index, element);
		Collections.sort(this.internalList, this.comparator);
		return e;
	}

	@Override
	public E remove(final int index) {
		final E e = this.internalList.remove(index);
		Collections.sort(this.internalList, this.comparator);
		return e;
	}

	@Override
	public E get(final int i) { return this.internalList.get(i); }

	@Override
	public int size() { return this.internalList.size(); }
}
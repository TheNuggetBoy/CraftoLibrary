package de.craftolution.craftolibrary;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<T> implements Builder<List<T>> {

	private final List<T> list = new ArrayList<>();

	private ListBuilder() { }

	/** TODO: Documentation */
	@Override
	public List<T> build() { return this.list; }
	
	/** TODO: Documentation */
	ListBuilder<T> add(T element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		this.list.add(element);
		return this;
	}

	/** TODO: Documentation */
	public static <T> ListBuilder<T> create(T element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		return new ListBuilder<T>().add(element);
	}

}
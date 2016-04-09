package de.craftolution.craftolibrary;

import java.util.Iterator;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class TupleIterator<T> implements Iterator<T> {

	private final ImmutableTuple<T> tokens;
	private int index = 0;

	TupleIterator(final ImmutableTuple<T> tokens) { this.tokens = tokens; }

	@Override
	public boolean hasNext() {
		return this.index < this.tokens.length();
	}

	@Override
	public T next() {
		final T element = this.tokens.at(this.index);
		this.index++;
		return element;
	}

	@Override
	public void remove() {
		if (this.tokens instanceof Tuple) {
			((Tuple<T>) this.tokens).at(this.index - 1, null);
		}
	}

}
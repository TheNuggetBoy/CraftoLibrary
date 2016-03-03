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
	private int index;

	TupleIterator(ImmutableTuple<T> tokens) { this.tokens = tokens; }

	@Override
	public boolean hasNext() {
		return this.index < (tokens.length() - 1);
	}

	@Override
	public T next() {
		T element = this.tokens.at(this.index);
		index++;
		return element;
	}

	@Override
	public void remove() {
		if (this.tokens instanceof Tuple) {
			((Tuple<T>) this.tokens).at(this.index - 1, null);
		}
	}

}
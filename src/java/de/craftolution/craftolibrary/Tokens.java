package de.craftolution.craftolibrary;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.Nullable;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 08.02.2016
 */
public class Tokens<T> {

	private final T[] tokens;
	private boolean empty;
	private boolean needsNewEmptyCalculation;

	Tokens(final T[] tokens) { this.tokens = tokens; }

	/** TODO: Documentation */
	public T at(final int index) {
		return this.tokens[index];
	}

	/** TODO: Documentation */
	public T at(final int index, @Nullable final T newValue) {
		this.tokens[index] = newValue;

		if (newValue != null) { this.empty = false; }
		else { this.needsNewEmptyCalculation = true; }

		return newValue;
	}

	/** TODO: Documentation */
	public boolean isEmpty() {
		if (this.needsNewEmptyCalculation) {
			this.needsNewEmptyCalculation = false;
			this.empty = true;
			for (final T t : this.tokens) {
				if (t != null) { this.empty = false; }
			}
		}
		return this.empty;
	}

	/** TODO: Documentation */
	public int length() { return this.tokens.length; }

	/** TODO: Documentation */
	public Stream<T> stream() { return Arrays.stream(this.tokens); }

	/** TODO: Documentation */
	public Stream<T> parallelStream() { return this.stream().parallel(); }

	/** TODO: Documentation */
	public T[] toArray() { return this.tokens; }

	/** TODO: Documentation */
	public Tokens<T> forEach(final Consumer<T> consumer) {
		for (final T t : this.tokens) { consumer.accept(t); }
		return this;
	}

	/** TODO: Documentation */
	public boolean contains(final T object) {
		if (object == null) { return false; }
		for (final T t : this.tokens) { if (t != null && object.equals(t)) { return true; } }
		return false;
	}

	@SafeVarargs
	public static <T> Tokens<T> of(final T... tokens) {
		return new Tokens<T>(tokens);
	}

	/** TODO: Documentation */
	public static <T> Tokens<T> of(final Collection<T> tokens) {
		@SuppressWarnings("unchecked")
		final
		T[] tokensArray = (T[]) tokens.toArray();
		return new Tokens<T>(tokensArray);
	}

	/** TODO: Documentation */
	public static <T> Tokens<T> of(final Tokens<T> original) {
		return new Tokens<T>(original.tokens);
	}

}
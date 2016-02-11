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

	Tokens(T[] tokens) { this.tokens = tokens; }

	/** TODO: Documentation */
	public T at(int index) { 
		return this.tokens[index];
	}

	/** TODO: Documentation */
	public T at(int index, @Nullable T newValue) {
		this.tokens[index] = newValue;

		if (newValue != null) { this.empty = false; }
		else { this.needsNewEmptyCalculation = true; }

		return newValue;
	}

	/** TODO: Documentation */
	public boolean isEmpty() {
		if (needsNewEmptyCalculation) {
			this.needsNewEmptyCalculation = false;
			this.empty = true;
			for (T t : this.tokens) {
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
	public Tokens<T> forEach(Consumer<T> consumer) {
		for (T t : this.tokens) { consumer.accept(t); }
		return this;
	}

	/** TODO: Documentation */
	public boolean contains(T object) {
		if (object == null) { return false; }
		for (T t : this.tokens) { if (t != null && object.equals(t)) { return true; } }
		return false;
	}

	@SafeVarargs
	public static <T> Tokens<T> of(T... tokens) {
		return new Tokens<T>(tokens);
	}

	/** TODO: Documentation */
	public static <T> Tokens<T> of(Collection<T> tokens) {
		@SuppressWarnings("unchecked")
		T[] tokensArray = (T[]) tokens.toArray();
		return new Tokens<T>(tokensArray);
	}

	/** TODO: Documentation */
	public static <T> Tokens<T> of(Tokens<T> original) {
		return new Tokens<T>(original.tokens);
	}

}
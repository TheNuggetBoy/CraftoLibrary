package de.craftolution.craftolibrary;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 08.02.2016
 */
public class Tokens<T> {

	private final T[] tokens;
	
	Tokens(T[] tokens) { this.tokens = tokens; }

	/** TODO: Documentation */
	public T at(int index) { 
		return this.tokens[index];
	}

	/** TODO: Documentation */
	public T at(int index, T newValue) {
		this.tokens[index] = newValue;
		return newValue;
	}

	/** TODO: Documentation */
	public int length() { return this.tokens.length; }
	
	@SafeVarargs
	public static <T> Tokens<T> of(T... tokens) {
		return new Tokens<T>(tokens);
	}

	/** TODO: Documentation */
	public static <T> Tokens<T> of(Tokens<T> original) {
		return new Tokens<T>(original.tokens);
	}

}
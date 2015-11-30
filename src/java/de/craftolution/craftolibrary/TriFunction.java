package de.craftolution.craftolibrary;

/**
 * Represents a function that accepts three arguments and produces a result. This is the three-arity specialization of Function.
 * This is a functional interface whose functional method is apply(Object, Object, Object).
 *
 * @param <F> - the type of the first argument to the operation
 * @param <S> - the type of the second argument to the operation
 * @param <T> - the type of the third argument to the operation
 * @param <R> - the type of the result of this operation
 * @author Fear837
 * @since 02.11.2015
 */
@FunctionalInterface
public interface TriFunction<F, S, T, R> {

	/**
	 * Applies this function to the given arguments.
	 * @param first - the first function argument
	 * @param second - the second function argument
	 * @param third - the third function argument
	 * @return the function result
	 */
	R apply(F first, S second, T third);

}
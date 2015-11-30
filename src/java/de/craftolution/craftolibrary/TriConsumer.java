package de.craftolution.craftolibrary;

/**
 * Represents an operation that accepts three input arguments and returns no result.
 * This is the three-arity specialization of Consumer. Unlike most other functional interfaces, TriConsumer is expected to operate via side-effects.
 * This is a functional interface whose functional method is accept(Object, Object, Object).
 *
 * @param <F> - the type of the first argument to the operation
 * @param <S> - the type of the second argument to the operation
 * @param <T> - the type of the third argument to the operation
 * @author Fear837
 * @since 01.11.2015
 */
@FunctionalInterface
public interface TriConsumer<F, S, T> {

	/**
	 * Performs this operation on the given arguments.
	 * @param first - the first input argument
	 * @param second - the second input argument
	 * @param third - the third input argument
	 */
	void accept(F first, S second, T third);

	/**
	 * Returns a composed BiConsumer that performs, in sequence, this operation followed by the after operation.
	 * If performing either operation throws an exception, it is relayed to the caller of the composed operation.
	 * If performing this operation throws an exception, the after operation will not be performed.
	 * @param after - the operation to perform after this operation
	 * @return a composed BiConsumer that performs in sequence this operation followed by the after operation
	 */
	default TriConsumer<F, S, T> andThen(final TriConsumer<? super F, ? super S, ? super T> after) {
		return (first, second, third) -> {
			this.accept(first, second, third);
			after.accept(first, second, third);
		};
	}

}
package de.craftolution.craftolibrary;

/**
 * Represents an operation that accepts four input arguments and returns no result.
 * This is the four-arity specialization of Consumer. Unlike most other functional interfaces, QuadConsumer is expected to operate via side-effects.
 * This is a functional interface whose functional method is accept(Object, Object, Object, Object).
 *
 * @param <F> - the type of the first argument to the operation
 * @param <S> - the type of the second argument to the operation
 * @param <T> - the type of the third argument to the operation
 * @param <T> - the type of the fourth argument to the operation
 * @author Fear837
 * @since 02.11.2015
 */
public interface QuadConsumer<First, Second, Third, Fourth> {

	/**
	 * Performs this operation on the given arguments.
	 * @param first - the first input argument
	 * @param second - the second input argument
	 * @param third - the third input argument
	 * @param fourth - the fourth input argument
	 */
	void accept(First first, Second second, Third third, Fourth fourth);

	/**
	 * Returns a composed BiConsumer that performs, in sequence, this operation followed by the after operation.
	 * If performing either operation throws an exception, it is relayed to the caller of the composed operation.
	 * If performing this operation throws an exception, the after operation will not be performed.
	 * @param after - the operation to perform after this operation
	 * @return a composed BiConsumer that performs in sequence this operation followed by the after operation
	 */
	default QuadConsumer<First, Second, Third, Fourth> andThen(final QuadConsumer<? super First, ? super Second, ? super Third, ? super Fourth> after) {
		return (first, second, third, fourth) -> {
			this.accept(first, second, third, fourth);
			after.accept(first, second, third, fourth);
		};
	}

}
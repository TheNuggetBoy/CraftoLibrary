/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

/**
 * Represents a function that accepts four arguments and produces a result. This is the four-arity specialization of Function.
 * This is a functional interface whose functional method is apply(Object, Object, Object, Object).
 *
 * @param <First> - the type of the first argument to the operation
 * @param <Second> - the type of the second argument to the operation
 * @param <Third> - the type of the third argument to the operation
 * @param <Fourth> - the type of the third argument to the operation
 * @param <Returned> - the type of the result of this operation
 * @author Fear837
 * @since 02.11.2015
 */
public interface QuadFunction<First, Second, Third, Fourth, Returned> {

	/**
	 * Applies this function to the given arguments.
	 * @param first - the first function argument
	 * @param second - the second function argument
	 * @param third - the third function argument
	 * @param fourth - the fourth function argument
	 * @return the function result
	 */
	Returned apply(First first, Second second, Third third, Fourth fourth);

}
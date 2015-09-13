/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import javax.annotation.Nullable;

/**
 * TODO: File description for ResultHandler.java
 *
 * @param <E> - The object that will be handled by the {@link #onResult(Object)} method.
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 28.01.2015 18:47:43
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
@FunctionalInterface
public interface ResultHandler<E> {

	/**
	 * Handles the result.
	 * @param result - The result, which could be {@code null}.
	 */
	void onResult(@Nullable final E result);
}
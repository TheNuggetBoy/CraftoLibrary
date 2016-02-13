/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.attributes;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.02.2016
 * @param <T>
 */
public interface Lengthable<T> {


	/** Specifies the max length a value in this column is allowed to have. */
	T length(int length);

}
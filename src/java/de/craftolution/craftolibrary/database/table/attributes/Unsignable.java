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
public interface Unsignable<T> {


	/** Specifies that this column only allows positive numbers. */
	T unsigned();

}
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
 * @author Kevin
 * @since 18.12.2015
 */
public interface AutoIncrementable<T> {

	/** Specifies that this column should automatically increment its standard value by each insert. */
	T autoIncrement();

}
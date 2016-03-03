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
 * @since 18.12.2015
 */
public interface DoubleLenghtable<T> {

	/**
	 * Specifies the range and precision of this column typically on columns with a decimal number.
	 *
	 * @param firstLength - The range of the column
	 * @param secondLength - The precision of the column
	 * @return Returns itself for builder purposes.
	 */
	T length(int firstLength, int secondLength);

}
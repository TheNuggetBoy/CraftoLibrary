/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.DataType;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 18.12.2015
 */
public class ColumnDefinitionBuilder {

	DataType type;
	Integer firstLength;
	Integer secondLength;
	boolean autoIncrement = false;
	boolean unsigned = false;
	String[] enumValues;

	ColumnDefinitionBuilder type(final DataType type) {
		this.type = type;
		return this;
	}

	ColumnDefinitionBuilder length(final int firstLength) {
		this.firstLength = firstLength;
		return this;
	}

	ColumnDefinitionBuilder length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	ColumnDefinitionBuilder autoIncrement(final boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
		return this;
	}

	ColumnDefinitionBuilder unsigned(final boolean unsigned) {
		this.unsigned = unsigned;
		return this;
	}

	ColumnDefinitionBuilder values(String[] values) {
		this.enumValues = values;
		return this;
	}

}
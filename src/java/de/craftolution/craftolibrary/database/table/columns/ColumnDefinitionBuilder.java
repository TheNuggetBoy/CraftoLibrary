/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

import de.craftolution.craftolibrary.database.table.DataType;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 18.12.2015
 */
public class ColumnDefinitionBuilder implements Serializable {

	private static final long serialVersionUID = 4642583947342132932L;

	DataType type;
	Integer firstLength;
	Integer secondLength;
	boolean autoIncrement = false;
	boolean unsigned = false;
	String[] enumValues;
	String onUpdate;

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

	ColumnDefinitionBuilder values(final String[] values) {
		this.enumValues = values;
		return this;
	}

	ColumnDefinitionBuilder onUpdate(final String onUpdate) {
		this.onUpdate = onUpdate;
		return this;
	}

}
package de.craftolution.craftolibrary.database;

import java.util.NoSuchElementException;

public class Row {

	private final QueryResult result;
	private final int rowIndex;

	Row(final QueryResult result, final int rowIndex) {
		this.result = result;
		this.rowIndex = rowIndex;
	}

	int getInt(final String columnName) throws NoSuchElementException { return 0; }

	String getString(final String columnName) throws NoSuchElementException { return null; }

	// ...
}
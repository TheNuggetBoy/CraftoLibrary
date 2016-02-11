package de.craftolution.craftolibrary.database;

import java.util.NoSuchElementException;

public class Row {

	private final QueryResult result;
	private final int rowIndex;
	
	Row(QueryResult result, int rowIndex) {
		this.result = result;
		this.rowIndex = rowIndex;
	}
	
	int getInt(String columnName) throws NoSuchElementException { return 0; }

	String getString(String columnName) throws NoSuchElementException { return null; }

	// ...
}
package de.craftolution.craftolibrary.database.result;

import java.util.NoSuchElementException;

public interface Row {

	int getInt(String columnName) throws NoSuchElementException ;

	String getString(String columnName) throws NoSuchElementException;

	// ...
}
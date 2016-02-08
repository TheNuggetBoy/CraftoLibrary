package de.craftolution.craftolibrary.database.table;

import java.util.Optional;

import de.craftolution.craftolibrary.database.query.Query;

public interface Column {

	String getName();

	Optional<String> getComment();

	boolean isNullable();

	Query toQuery() throws IllegalStateException;

}
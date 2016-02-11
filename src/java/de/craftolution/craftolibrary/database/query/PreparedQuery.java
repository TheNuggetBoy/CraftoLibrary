package de.craftolution.craftolibrary.database.query;

import java.util.function.Function;

import de.craftolution.craftolibrary.Tokens;
import de.craftolution.craftolibrary.database.Database;
import de.craftolution.craftolibrary.database.QueryResult;

public class PreparedQuery<Input> {

	private final Database database;
	private final Query query;
	private final Function<Input, Tokens<Object>> converter;

	PreparedQuery(final Database database, final Query query, final Function<Input, Tokens<Object>> converter) {
		this.database = database;
		this.query = query;
		this.converter = converter;
	}

	Query getQuery() { return null; }

	boolean isClosed() { return false; }

	void close() { }

	QueryResult execute(final Input input) { return null; }

}
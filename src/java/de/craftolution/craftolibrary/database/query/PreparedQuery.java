package de.craftolution.craftolibrary.database.query;

import de.craftolution.craftolibrary.database.result.QueryResult;

public interface PreparedQuery<Input> {

	Query getQuery();

	boolean isClosed();

	void close();

	QueryResult execute(Input input);

}
package de.craftolution.craftolibrary.database.query;

import de.craftolution.craftolibrary.database.result.QueryResult;

public interface BiPreparedQuery<FirstInput, SecondInput> {

	Query getQuery();

	boolean isClosed();

	void close();

	QueryResult execute(FirstInput firstInput, SecondInput secondInput);

}
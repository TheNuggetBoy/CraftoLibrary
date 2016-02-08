package de.craftolution.craftolibrary.database.query;

import de.craftolution.craftolibrary.database.result.QueryResult;

public interface TriPreparedQuery<FirstInput, SecondInput, ThirdInput> {

	Query getQuery();

	boolean isClosed();

	void close();

	QueryResult execute(FirstInput firstInput, SecondInput secondInput, ThirdInput thirdInput);

}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import de.craftolution.craftolibrary.Pair;
import de.craftolution.craftolibrary.Tokens;
import de.craftolution.craftolibrary.database.Database;
import de.craftolution.craftolibrary.database.QueryResult;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 11.02.2016
 * @param <FirstInput>
 * @param <SecondInput>
 */
public class BiPreparedQuery<FirstInput, SecondInput> {

	private PreparedQuery<Pair<FirstInput, SecondInput>> preparedQuery;

	/** TODO: Documentation */
	public BiPreparedQuery(Database database, Query query, BiFunction<FirstInput, SecondInput, Tokens<Object>> converter, Consumer<Exception> exceptionHandler) {
		this.preparedQuery = new PreparedQuery<Pair<FirstInput, SecondInput>>(database, query, (input) -> converter.apply(input.getFirst(), input.getSecond()), exceptionHandler);
	}

	/** TODO: Documentation */
	public Query getQuery() { return this.preparedQuery.getQuery(); }

	/** TODO: Documentation */
	public boolean isClosed() { return this.preparedQuery.isClosed(); }

	/** TODO: Documentation */
	public void close() { this.preparedQuery.close(); }

	/** TODO: Documentation */
	public QueryResult execute(FirstInput firstInput, SecondInput secondInput) {
		return this.preparedQuery.execute(Pair.of(firstInput, secondInput));
	}

}
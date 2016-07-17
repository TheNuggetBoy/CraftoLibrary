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
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.Database;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 11.02.2016
 * @param <FirstInput>
 * @param <SecondInput>
 */
public class BiPreparedQuery<FirstInput, SecondInput> {

	private final PreparedQuery<Pair<FirstInput, SecondInput>> preparedQuery;

	/** TODO: Documentation */
	public BiPreparedQuery(final Database database, final Query query, final BiFunction<FirstInput, SecondInput, Tuple<Object>> converter, final Consumer<Exception> exceptionHandler) {
		this.preparedQuery = new PreparedQuery<Pair<FirstInput, SecondInput>>(database, query, (input) -> converter.apply(input.getFirst(), input.getSecond()), exceptionHandler);
	}

	/** TODO: Documentation */
	public Query getQuery() { return this.preparedQuery.getQuery(); }

	/** TODO: Documentation */
	public boolean isClosed() { return this.preparedQuery.isClosed(); }

	/** TODO: Documentation */
	public void close() { this.preparedQuery.close(); }

	/** TODO: Documentation */
	public PreparedQueryResult<Pair<FirstInput, SecondInput>> execute(final FirstInput firstInput, final SecondInput secondInput) {
		return this.preparedQuery.execute(Pair.of(firstInput, secondInput));
	}

	/** TODO: Documentation */
	public Query buildQuery(final FirstInput firstInput, final SecondInput secondInput) {
		return this.preparedQuery.buildQuery(Pair.of(firstInput, secondInput));
	}

}
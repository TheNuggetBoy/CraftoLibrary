/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.util.function.Consumer;

import de.craftolution.craftolibrary.TriFunction;
import de.craftolution.craftolibrary.Triple;
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.Database;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 11.02.2016
 * @param <FirstInput>
 * @param <SecondInput>
 * @param <ThirdInput>
 */
public class TriPreparedQuery<FirstInput, SecondInput, ThirdInput> {

	private final PreparedQuery<Triple<FirstInput, SecondInput, ThirdInput>> preparedQuery;

	/** TODO: Documentation */
	public TriPreparedQuery(final Database database, final Query query, final TriFunction<FirstInput, SecondInput, ThirdInput, Tuple<Object>> converter, final Consumer<Exception> exceptionHandler) {
		this.preparedQuery = new PreparedQuery<Triple<FirstInput, SecondInput, ThirdInput>>(database, query, (input) -> converter.apply(input.getFirst(), input.getSecond(), input.getThird()), exceptionHandler);
	}

	/** TODO: Documentation */
	public Query getQuery() { return this.preparedQuery.getQuery(); }

	/** TODO: Documentation */
	public boolean isClosed() { return this.preparedQuery.isClosed(); }

	/** TODO: Documentation */
	public void close() { this.preparedQuery.close(); }

	/** TODO: Documentation */
	public PreparedQueryResult<Triple<FirstInput, SecondInput, ThirdInput>> execute(final FirstInput firstInput, final SecondInput secondInput, final ThirdInput thirdInput) {
		return this.preparedQuery.execute(Triple.of(firstInput, secondInput, thirdInput));
	}

	/** TODO: Documentation */
	public Query buildQuery(final FirstInput firstInput, final SecondInput secondInput, final ThirdInput thirdInput) {
		return this.preparedQuery.buildQuery(Triple.of(firstInput, secondInput, thirdInput));
	}

}
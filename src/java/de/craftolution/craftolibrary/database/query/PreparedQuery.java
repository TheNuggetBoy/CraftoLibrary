/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.Database;
import de.craftolution.craftolibrary.database.QueryResult;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 11.02.2016
 * @param <Input>
 */
public class PreparedQuery<Input> {

	final Database database;
	final Query query;
	final Function<Input, Tuple<Object>> converter;
	final String rawQuery;
	final Consumer<Exception> exceptionHandler;

	@Nullable PreparedStatement statement;

	/** TODO: Documentation */
	public PreparedQuery(final Database database, final Query query, final Function<Input, Tuple<Object>> converter, final Consumer<Exception> exceptionHandler) {
		this.database = database;
		this.query = query.clone();
		this.converter = converter;
		this.rawQuery = query.toString();
		this.exceptionHandler = exceptionHandler;

		final Result<PreparedStatement> stmnt = this.database.prepareStatement(query);
		if (stmnt.isPresent()) { this.statement = stmnt.get(); }
	}

	/** TODO: Documentation */
	public Query getQuery() { return this.query; }

	/** TODO: Documentation */
	public boolean isClosed() {
		try { return this.statement != null && !this.statement.isClosed(); }
		catch (final SQLException ignore) { return true; }
	}

	/** TODO: Documentation */
	public void close() {
		if (this.statement != null) {
			try { this.statement.close(); }
			catch (final SQLException ignore) { }
		}
	}

	/** TODO: Documentation */
	public PreparedQueryResult<Input> execute(final Input input) {
		final Tuple<Object> variables = this.converter.apply(input);

		try {
			if (this.statement != null) {
				if (this.statement.isClosed()) { // Try to reopen the statement, if closed
					final Result<PreparedStatement> result = this.database.prepareStatement(this.query);
					if (result.getException().isPresent()) { this.exceptionHandler.accept(result.getException().get()); }
					else if (result.isPresent()) { this.statement = result.get(); }
				}

				long start, duration;
				synchronized (this.statement) {
					this.statement.clearParameters();

					for (int i = 0; i < variables.length(); i++) {
						this.statement.setObject(i + 1, variables.at(i));
					}

					start = System.nanoTime();
					this.statement.execute();
					duration = System.nanoTime() - start;
				}

				return new PreparedQueryResult<Input>(this.database, this.query, this.statement.getUpdateCount(), this.statement, this.statement.getResultSet(), null, this.exceptionHandler, 
						Duration.ofNanos(duration), input, variables, false, this);
			}
			else {
				String query = this.rawQuery;
				for (int i = 0; i < variables.length(); i++) {
					query = query.replaceFirst("\\?", "'" + variables.at(i) + "'");
				}

				final Query realQuery = Query.of(query);
				final QueryResult result = this.database.execute(realQuery);
				return new PreparedQueryResult<Input>(this.database, realQuery, result.getAffectedRows(), result.getStatement().orElse(null), result.getResultSet().orElse(null), result.getException().orElse(null), 
						this.exceptionHandler, result.getExecutionDuration(), input, variables, true, this);
			}
		}
		catch (final SQLException e) { return new PreparedQueryResult<Input>(this.database, this.query, 0, null, null, e, this.exceptionHandler, Duration.ZERO, input, variables, false, this); }
	}

	/** TODO: Documentation */
	public Query buildQuery(Input input) {
		final Tuple<Object> variables = this.converter.apply(input);

		String query = this.rawQuery;
		for (int i = 0; i < variables.length(); i++) {
			query = query.replaceFirst("\\?", "'" + variables.at(i) + "'");
		}
		return Query.of(query);
	}

}
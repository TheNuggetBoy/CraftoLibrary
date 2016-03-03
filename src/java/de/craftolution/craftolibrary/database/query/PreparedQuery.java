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

	private final Database database;
	private final Query query;
	private final Function<Input, Tuple<Object>> converter;
	private final String rawQuery;
	private final Consumer<Exception> exceptionHandler;

	@Nullable private PreparedStatement statement;

	/** TODO: Documentation */
	public PreparedQuery(final Database database, final Query query, final Function<Input, Tuple<Object>> converter, final Consumer<Exception> exceptionHandler) {
		this.database = database;
		this.query = query.clone();
		this.converter = converter;
		this.rawQuery = query.toString();
		this.exceptionHandler = exceptionHandler;
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
	public QueryResult execute(final Input input) {
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

				return new QueryResult(this.database, this.query, this.statement.getUpdateCount(), this.statement, this.statement.getResultSet(), null, this.exceptionHandler, Duration.ofNanos(duration));
			}
			else {
				String query = this.rawQuery;
				for (int i = 0; i < variables.length(); i++) {
					query = query.replace("?", "'" + variables.at(i) + "'");
				}
				return this.database.execute(Query.of(query));
			}
		}
		catch (final SQLException e) { return new QueryResult(this.database, this.query, 0, null, null, e, this.exceptionHandler, Duration.ZERO); }
	}

}
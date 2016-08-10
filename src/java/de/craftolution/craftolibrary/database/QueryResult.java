/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.Stopwatch;
import de.craftolution.craftolibrary.database.query.Query;

/**
 * A {@code QueryResult} pretty much serves as a container of
 * information about what happened during and after the exeuction of a certain {@link Query}.
 *
 * It contains the actual {@link Query}, the amount of rows affected, the statement used, how long it took
 * to execute the query and, if the query is a select query, a ResultSet.
 *
 * Additionally the {@link QueryResult} provides {@link #getRows()} as an alternative to {@link #getResultSet()}
 * for easier looping (for) and without nasty exceptions that you would have to try/catch.
 *
 * @author Fear837
 * @since 12.02.2016
 */
public class QueryResult implements Serializable {

	private static final long serialVersionUID = 6283956960990259855L;

	private int affectedRows = 0;

	private final Query query;
	private final List<Row> rows = Lists.newArrayList();
	private final List<String> columns = Lists.newArrayList();
	private final Duration executionDuration;

	@Nullable private final Statement statement;
	@Nullable private final ResultSet result;
	@Nullable private Exception exception;

	@Nullable private List<Integer> generatedKeys;

	/**
	 * Initializes a new {@link QueryResult}. In most cases, only the {@link Database} instances
	 * should be using this constructor.
	 *
	 * @param database - The database
	 * @param query - The query
	 * @param affectedRows - The amount of affected rows
	 * @param statement - The statement
	 * @param result - The result
	 * @param exception - The exception, if there's any
	 * @param exceptionReporter - An exception reporter
	 * @param executionDuration - The execution duration
	 */
	public QueryResult(final Database database, final Query query, final int affectedRows, final Statement statement, final ResultSet result, final Exception exception, final Consumer<Exception> exceptionReporter, final Duration executionDuration) {
		Check.notNull(database, "The database cannot be null!");
		this.query = query;
		this.statement = statement;
		this.result = result;
		this.exception = exception;
		this.executionDuration = executionDuration;

		if (result != null) {
			try {
				for (int i = 0; i < this.result.getMetaData().getColumnCount(); i++) {
					this.columns.add(this.result.getMetaData().getColumnLabel(i + 1));
				}

				final Stopwatch stopwatch = Stopwatch.start();
				for (int i = 0; result.next() && !stopwatch.hasPassed(Duration.ofSeconds(1)); i++) {
					this.rows.add(new Row(this, i));
					this.affectedRows++;
				}

				result.beforeFirst();
			}
			catch (final Exception e) { if (this.exception == null) { this.exception = e; } exceptionReporter.accept(e); }
		}

		if (this.affectedRows == 0) { this.affectedRows = affectedRows; }
	}

	/** @return Returns a list of keys that were created when the query got executed. */
	public List<Integer> getGeneratedKeys() {
		if (this.generatedKeys != null) { return this.generatedKeys; }

		if (this.statement != null) {
			try {
				final ResultSet generatedKeys = this.statement.getGeneratedKeys();
				final List<Integer> keyList = Lists.newArrayList();

				final Stopwatch stopwatch = Stopwatch.start();
				for (int index = 1; generatedKeys.next() && !stopwatch.hasPassed(Duration.ofSeconds(1)); index++) {
					keyList.add(generatedKeys.getInt(index));
				}

				this.generatedKeys = keyList;
				return this.generatedKeys;
			}
			catch (final SQLException ignore) { }
		}
		return Collections.emptyList();
	}

	/** @return Returns the query used that produced this result instance. */
	public Query getQuery() { return this.query; }

	/** @return Returns how many rows were affected by the query. */
	public int getAffectedRows() { return this.affectedRows; }

	/** @return Returns the {@link Statement} that was created for executing the query. */
	public Optional<Statement> getStatement() { return Optional.ofNullable(this.statement); }

	/** @return Returns a {@link ResultSet} if one was returned by the execution of the query.  */
	public Optional<ResultSet> getResultSet() { return Optional.ofNullable(this.result); }

	/** @return Returns an {@link Exception} if any occured while trying to execute the query. */
	public Optional<Exception> getException() { return Optional.ofNullable(this.exception); }

	/** @return Returns the columns of the returned {@link ResultSet}. Note: If no {@link #getResultSet()} is present, this method returns an empty list. */
	public List<String> getColumns() { return this.columns; }

	/** @return Returns a list of rows included in the {@link ResultSEt}. Note: If no {@link #getResultSet()} is present, this method returns an empty list. */
	public List<Row> getRows() { return this.rows; }

	/** @return Returns how long it took to execute the query. */
	public Duration getExecutionDuration() { return this.executionDuration; }

	/** @return Returns whether or not the amount of affected rows is greater than 0 and if no exception is present. */
	public boolean wasSuccess() { return this.getAffectedRows() > 0 && !this.getException().isPresent(); }

	/** TODO: Documentation */
	public QueryResult ifSuccess(final Runnable runnable) { if (this.wasSuccess()) { Check.notNull(runnable, "The runnable cannot be null!").run(); } return this; }

	/** TODO: Documentation */
	public QueryResult ifSuccess(final Consumer<QueryResult> consumer) { if (this.wasSuccess()) { Check.notNull(consumer, "The consumer cannot be null!").accept(this); } return this; }

	/** TODO: Documentation */
	public QueryResult ifFailure(final Runnable runnable) { if (!this.wasSuccess()) { Check.notNull(runnable, "The consumer cannot be null!").run(); } return this; }

	/** TODO: Documentation */
	public QueryResult ifFailure(Consumer<QueryResult> consumer) { if (!this.wasSuccess()) { Check.notNull(consumer, "The consumer cannot be null!").accept(this); } return this; }

	/** TODO: Documentation */
	public QueryResult ifException(final Runnable runnable) { 
		if (this.getException().isPresent()) { Check.notNull(runnable, "The consumer cannot be null!").run(); }
		return this;
	}

	/** TODO: Documentation */
	public QueryResult ifException(Consumer<QueryResult> consumer) { 
		if (this.getException().isPresent()) { Check.notNull(consumer, "The consumer cannot be null!").accept(this); }
		return this;
	}

	/** TODO: Documentation */
	public boolean handle(final Consumer<Optional<Exception>> errorHandler, final Consumer<QueryResult> successHandler) {
		if (this.wasSuccess()) { successHandler.accept(this); }
		else { errorHandler.accept(this.getException()); }
		return this.wasSuccess();
	}

}
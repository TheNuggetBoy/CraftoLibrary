/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

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
import de.craftolution.craftolibrary.Instant;
import de.craftolution.craftolibrary.database.query.Query;

public class QueryResult {

	private int affectedRows;

	private final Query query;
	private final List<Row> rows = Lists.newArrayList();
	private final List<String> columns = Lists.newArrayList();

	@Nullable private final Statement statement;
	@Nullable private final ResultSet result;
	@Nullable private Exception exception;

	@Nullable private List<Integer> generatedKeys;

	public QueryResult(final Database database, Query query, final int affectedRows, final Statement statement, final ResultSet result, final Exception exception, final Consumer<Exception> exceptionReporter) {
		Check.notNull(database, "The database cannot be null!");
		this.query = query;
		this.affectedRows = affectedRows;
		this.statement = statement;
		this.result = result;
		this.exception = exception;

		if (result != null) {
			try {
				for (int i = 0; i < this.result.getMetaData().getColumnCount(); i++) {
					this.columns.add(this.result.getMetaData().getColumnLabel(i + 1));
				}

				final Instant instant = Instant.now();
				for (int i = 0; result.next() && !instant.hasPassed(Duration.ofSeconds(1)); i++) {
					this.rows.add(new Row(this, i));
					this.affectedRows++;
				}

				result.beforeFirst();
			}
			catch (final Exception e) { if (this.exception == null) { this.exception = e; } exceptionReporter.accept(e); }
		}
	}

	/** TODO: Documentation */
	public List<Integer> getGeneratedKeys() {
		if (this.generatedKeys != null) { return this.generatedKeys; }

		if (this.statement != null) {
			try {
				final ResultSet generatedKeys = this.statement.getGeneratedKeys();
				final List<Integer> keyList = Lists.newArrayList();

				final Instant instant = Instant.now();
				for (int index = 1; generatedKeys.next() && !instant.hasPassed(Duration.ofSeconds(1)); index++) {
					keyList.add(generatedKeys.getInt(index));
				}

				this.generatedKeys = keyList;
				return this.generatedKeys;
			}
			catch (final SQLException ignore) { }
		}
		return Collections.emptyList();
	}

	/** TODO: Documentation */
	public Query getQuery() { return this.query; }

	/** TODO: Documentation */
	public int getAffectedRows() { return this.affectedRows; }

	/** TODO: Documentation */
	public Optional<Statement> getStatement() { return Optional.ofNullable(this.statement); }

	/** TODO: Documentation */
	public Optional<ResultSet> getResultSet() { return Optional.ofNullable(this.result); }

	/** TODO: Documentation */
	public Optional<Exception> getException() { return Optional.ofNullable(this.exception); }

	/** TODO: Documentation */
	public List<String> getColumns() { return this.columns; }

	/** TODO: Documentation */
	public List<Row> getRows() { return this.rows; }

	/** TODO: Documentation */
	public boolean wasSuccess() { return this.getAffectedRows() > 0 && !this.getException().isPresent(); }

}
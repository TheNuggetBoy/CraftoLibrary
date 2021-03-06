/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.Scheduled;
import de.craftolution.craftolibrary.TriFunction;
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.query.BiPreparedQuery;
import de.craftolution.craftolibrary.database.query.PreparedQuery;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.query.SelectQuery;
import de.craftolution.craftolibrary.database.query.TriPreparedQuery;
import de.craftolution.craftolibrary.database.table.Table;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 08.02.2016
 */
public class MySQL implements Database {

	private Connection connection;

	private final transient String username;
	private final transient String password;
	private final transient String databaseName;
	private final transient String port;
	private final transient String hostname;

	private final Consumer<Exception> exceptionHandler;
	private final Consumer<Query> queryHandler;
	private final Consumer<String> logHandler;
	private final StatisticRecorder statistics;

	MySQL(final String username, final String password, final String databaseName, final String port, final String hostname, final Consumer<Exception> exceptionHandler, final Consumer<Query> queryHandler, final Consumer<String> logHandler, final boolean recordStatistics) {
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;
		this.port = port;
		this.hostname = hostname;
		this.exceptionHandler = exceptionHandler != null ? exceptionHandler : (exception) -> exception.printStackTrace();
		this.queryHandler = queryHandler != null ? queryHandler : (query) -> this.getConnection();
		this.logHandler = logHandler != null ? logHandler : (log) -> System.out.println(log);
		this.statistics = new StatisticRecorder(this, recordStatistics, this.logHandler, this.exceptionHandler);
	}

	@Override
	@Nullable
	public Connection getConnection() { return this.connection; }

	@Override
	public boolean isConnected() {
		try { return this.connection != null && !this.connection.isClosed(); }
		catch (final SQLException e) { this.exceptionHandler.accept(e); }
		return false;
	}

	@Override
	public Database connect() {
		if (this.isConnected()) { this.disconnect(); }

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.logHandler.accept("Connecting to jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.databaseName + "...");

			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.databaseName + "?allowMultiQueries=true&autoReconnect=true", this.username, this.password);

			this.logHandler.accept("Successfully connected.");
		}
		catch (final SQLException e) { this.exceptionHandler.accept(e); }
		catch (final ClassNotFoundException e) { this.exceptionHandler.accept(e); }

		return this;
	}

	@Override
	public Database disconnect() {
		if (this.connection != null) {
			try { this.connection.close(); }
			catch (final SQLException e) { this.exceptionHandler.accept(e); }

			this.statistics.stop();
		}

		return this;
	}

	@Override
	public QueryResult createTable(final Table table) {
		return this.execute(table.toQuery());
	}

	@Override
	public QueryResult truncateTable(Table table) {
		return this.execute(Query.of("TRUNCATE TABLE `" + table.getName() + "`;"));
	}

	@Override
	public QueryResult dropTable(Table table) {
		return this.execute(Query.of("DROP TABLE `" + table.getName() + "`;"));
	}

	@Override
	public Result<Statement> createStatement() {
		try {
			final Statement statement = this.connection.createStatement();
			return Result.of(statement);
		}
		catch (final SQLException e) { return Result.ofException(e); }
	}

	@Override
	public Result<PreparedStatement> prepareStatement(final Query query) {
		try {
			final PreparedStatement statement = this.connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			return Result.of(statement);
		}
		catch (final SQLException e) { return Result.ofException(e); }
	}

	@Override
	public <F> PreparedQuery<F> prepareQuery(final Query query, final Function<F, Tuple<Object>> converter) {
		Check.nonNulls("The query/converter cannot be null!", query, converter);
		return new PreparedQuery<F>(this, query, converter, this.exceptionHandler);
	}

	@Override
	public <F, S> BiPreparedQuery<F, S> prepareQuery(final Query query, final BiFunction<F, S, Tuple<Object>> converter) {
		Check.nonNulls("The query/converter cannot be null!", query, converter);
		return new BiPreparedQuery<F, S>(this, query, converter, this.exceptionHandler);
	}

	@Override
	public <F, S, T> TriPreparedQuery<F, S, T> prepareQuery(final Query query, final TriFunction<F, S, T, Tuple<Object>> converter) {
		Check.nonNulls("The query/converter cannot be null!", query, converter);
		return new TriPreparedQuery<F, S, T>(this, query, converter, this.exceptionHandler);
	}

	@Override
	public QueryResult execute(final Query query) {
		Check.notNull(query, "The query cannot be null!");

		if (!this.isConnected()) { this.connect(); }
		if (!this.isConnected()) { return new QueryResult(this, query, -1, null, null, null, this.exceptionHandler, Duration.ZERO); }
		
		// Determine whether or not the query expects a result
		final String rawQuery = query.toString();

		QueryResult result = null;
		Statement statement = null;
		ResultSet resultSet = null;
		int affectedRows = -1;
		final boolean isSelect = query instanceof SelectQuery || rawQuery.toUpperCase().contains("SELECT");

		try {
			this.queryHandler.accept(query);

			long start, duration;
			start = System.nanoTime();

			statement = this.connection.createStatement();

			if (isSelect) {
				resultSet = statement.executeQuery(rawQuery);
				affectedRows = this.getResultSize(resultSet);
			}
			else {
				affectedRows = statement.executeUpdate(rawQuery, Statement.RETURN_GENERATED_KEYS);
				statement.close();
			}

			duration = System.nanoTime() - start;

			result = new QueryResult(this, query, affectedRows, statement, resultSet, null, this.exceptionHandler, Duration.ofNanos(duration));
			if (this.statistics != null) { this.statistics.insertQuery(result); } // TODO: Investigate when this could be null!

			return result;
		}
		catch (final SQLException e) {
			result = new QueryResult(this, query, affectedRows, statement, resultSet, e, this.exceptionHandler, Duration.ZERO);
			this.statistics.insertQuery(result);
			return result;
		}
	}

	@Override
	public Scheduled<QueryResult> executeAsync(final Query query) {
		Check.notNull(query, "The query cannot be null!");
		return new Scheduled<QueryResult>(Duration.ZERO, true, () -> this.execute(query));
	}

	private int getResultSize(@Nullable final ResultSet result) {
		if (result != null) {
			try {
				final int currentRow = result.getRow();
				result.last();
				final int size = result.getRow();
				if (currentRow != 0) { result.absolute(currentRow); }
				else { result.beforeFirst(); }
				return size;
			}
			catch (final SQLException e) { this.exceptionHandler.accept(e); }
		}
		return -1;
	}

}
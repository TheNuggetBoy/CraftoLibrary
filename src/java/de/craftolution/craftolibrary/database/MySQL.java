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
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.TriFunction;
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

	MySQL(final String username, final String password, final String databaseName, final String port, final String hostname, final Consumer<Exception> exceptionHandler, final Consumer<Query> queryHandler, Consumer<String> logHandler, boolean recordStatistics) {
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
		catch (SQLException e) { this.exceptionHandler.accept(e); }
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
		catch (SQLException e) { this.exceptionHandler.accept(e); }
		catch (ClassNotFoundException e) { this.exceptionHandler.accept(e); }
		
		return this;
	}

	@Override
	public Database disconnect() {
		if (this.connection != null) {
			try { this.connection.close(); }
			catch (SQLException e) { this.exceptionHandler.accept(e); }
			
			this.statistics.stop();
		}

		return this;
	}

	@Override
	public Result<Statement> createTable(Table table) {
		QueryResult result = this.execute(table.toQuery());
		if (result.getStatement().isPresent()) {
			if (result.getException().isPresent()) {
				return Result.of(result.getStatement().get(), result.getException().get());
			}
			return Result.of(result.getStatement().get());
		}
		else if (result.getException().isPresent()) { return Result.ofException(result.getException().get()); }
		else { return Result.completeFailure(); }
	}

	@Override
	public Result<Statement> createStatement() {
		try {
			Statement statement = this.connection.createStatement();
			return Result.of(statement);
		}
		catch (SQLException e) { return Result.ofException(e); }
	}

	@Override
	public Result<PreparedStatement> prepareStatement(Query query) {
		try {
			PreparedStatement statement = this.connection.prepareStatement(query.toString());
			return Result.of(statement);
		}
		catch (SQLException e) { return Result.ofException(e); }
	}

	@Override
	public <F> PreparedQuery<F> prepareQuery(Query query, Function<F, Tuple<Object>> converter) {
		Check.nonNulls("The query/converter cannot be null!", query, converter);
		return new PreparedQuery<F>(this, query, converter, this.exceptionHandler);
	}

	@Override
	public <F, S> BiPreparedQuery<F, S> prepareQuery(Query query, BiFunction<F, S, Tuple<Object>> converter) {
		Check.nonNulls("The query/converter cannot be null!", query, converter);
		return new BiPreparedQuery<F, S>(this, query, converter, this.exceptionHandler);
	}

	@Override
	public <F, S, T> TriPreparedQuery<F, S, T> prepareQuery(Query query, TriFunction<F, S, T, Tuple<Object>> converter) {
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
		boolean isSelect = query instanceof SelectQuery || rawQuery.toUpperCase().contains("SELECT");

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
			this.statistics.insertQuery(result);

			return result;
		}
		catch (SQLException e) {
			result = new QueryResult(this, query, affectedRows, statement, resultSet, e, this.exceptionHandler, Duration.ZERO);
			this.statistics.insertQuery(result);
			return result;
		}
	}

	@Override
	public Scheduled<QueryResult> executeAsync(Query query) {
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
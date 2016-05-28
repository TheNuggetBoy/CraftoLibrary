/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.Scheduled;
import de.craftolution.craftolibrary.TriFunction;
import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.query.BiPreparedQuery;
import de.craftolution.craftolibrary.database.query.PreparedQuery;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.query.TriPreparedQuery;
import de.craftolution.craftolibrary.database.table.Table;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 08.02.2016
 */
public interface Database {

	/**
	 * Returns the {@link Connection} instance used by this {@link Database} for executing
	 * sql statements.
	 *
	 * Note that the {@link Connection} instance may or may not be present depending on whether
	 * or not the instance was ever connected to a external database yet.
	 *
	 * @return The connection to the database.
	 */
	@Nullable Connection getConnection();

	/** @return Returns whether or not this instance is currently connected to the database. */
	boolean isConnected();

	/** Opens the connection to the external databse. */
	Database connect();

	/** Closes the connection to the external database. */
	Database disconnect();

	/**
	 * Creates the specified {@link Table} by inserting it into the database.
	 * If the creation was successfull, the returned {@link Result} will contain a
	 * {@link Statement} used for inserting the table.
	 *
	 * @param table - The table to create
	 * @return Returns the {@link Statement} used for the creation.
	 */
	Result<Statement> createTable(Table table);

	/** TODO: Documentation */
	Result<Statement> truncateTable(Table table);

	/** TODO: Documentation */
	Result<Statement> dropTable(Table table);

	/**
	 * Creates a {@link Statement} object for sending SQL statements to the database. SQL statements without
	 * parameters are normally executed using {@link Statement} objects. If the SQL statement is
	 * executed many times, it may be more efficient to use a {@link PreparedQuery} or {@link PreparedStatement} object.
	 *
	 * @return A new default {@link Statement} object
	 */
	Result<Statement> createStatement();

	/**
	 * Creates a {@link PreparedStatement} object for sending parameterized SQL statements to the database.
	 *
	 * <p>A SQL statement with or without IN parameters can be pre-compiled and stored in a
	 * {@link PreparedStatement} object. This object can then be used to efficiently execute this statement multiple times.</p>
	 *
	 * <p><b>Note:</b> This method is optimized for handling parametric SQL statements that benefit from
	 * precompilation. If the driver supports precompilation, the method prepareStatement will send
	 * the statement to the database for precompilation. Some drivers may not support precompilation.
	 * In this case, the statement may not be sent to the database until the PreparedStatement object
	 * is executed. This has no direct effect on users; however, it does affect which methods throw
	 * certain SQLException objects. </p>
	 *
	 * @param query - The query that may contain one or more '?' IN parameter placeholders.
	 * @return A new default {@link PreparedStatement} object containing the pre-compiled SQL statement.
	 */
	Result<PreparedStatement> prepareStatement(Query query);

	/**
	 * Creates a {@link PreparedQuery} object for sending parameterized SQL statements to the database.
	 *
	 * <p>A {@link PreparedQuery} is similiar to a {@link PreparedStatement} but has some slight improvements regarding execution
	 * and error handling. Its aims to simplify and unite precompilable select and execute queries by allowing both and only returning
	 * a {@link ResultSet} in the {@link QueryResult} if possible. <p>
	 *
	 * <p><b>Usage example:</b><br> To use a {@link PreparedQuery} it first needs to be initialized: <br>
	 * <code>PreparedQuery{@literal <User>} insertUserQuery = myDatabase.prepareQuery(Query.of("INSERT INTO `users` (`id`, `name`) VALUES (?, ?);"), user -> Tuple.of(user.getId(), user.getName());</code> <br>
	 * Later you can use the query by calling: {@link PreparedQuery#execute(Object)}.
	 *
	 * <p> The whole thing can be split into 3 parts:
	 * <br> <b>1. The declaration:</b> <code>PreparedQuery{@literal <User>} insertUserQuery;</code> <br>
	 * In the first step you specify what type of input is needed for the query to be executed.
	 * In this case later you'll have to write <code>insertUserQuery.execute(myUser);</code> where {@code myUser} is an instance of {@code User}.
	 * <br> <b>2. The query:</b> <code>myDatabase.prepareQuery(Query.of("INSERT .... VALUES (?, ?)"), ...);</code> <br>
	 * Specifing the query is very similiar to a {@link PreparedStatement}. Basically if you have parameters in your query you write them with {@code '?'} in your query.
	 * These characters will later be replaced by the converted tokens specified by the converter in the next step. Just keep in mind to have the same amount of ?'s as your tokenconverter provides.
	 * <br> <b>3. The converter:</b> <code>myDatabase.prepareQuery(..., user -> Tuple.of(user.getId(), user.getName()));</code> <br>
	 * In this step we're converting the input (in this case a user instance) basically to an array (tokens instance) of parameters which then
	 * replace the ?'s in the query. The order is very important so better check if everything matches. </p>
	 *
	 * <p> The second improvement to the {@link PreparedStatement} is its failure safety. Sometimes a {@link PreparedStatement} could've been closed already or not created at all.
	 * Although using an {@link PreparedStatement} in the underlying implementation, the {@link PreparedQuery} can always fall back to a standard raw query if the statement is not available.
	 * And if even that fails, it'll just return a {@link QueryResult} with a failure state and probably an exception stored. So no {@code null}-check or try/catch needed here. </p>
	 *
	 * @param <F> - What kind of input object is needed for this query.
	 * @param query - The query used for execution. Probably contains some '?' characters for parameters.
	 * @param converter - The converter used to convert the {@literal <F>} input object to an array of parameters.
	 * @return Returns a {@link PreparedQuery} ready to be used.
	 */
	<F> PreparedQuery<F> prepareQuery(Query query, Function<F, Tuple<Object>> converter);

	/**
	 * Creates a {@link BiPreparedQuery} object for sending parameterized SQL statements to the database.
	 * This time with two acceptable input objects.
	 *
	 * <p>Read {@link #prepareQuery(Query, Function)} for further information on how to use a {@link PreparedQuery}.
	 * A {@link BiPreparedQuery} can be executed the same, but is able to accept two input objects.</p>
	 *
	 * @param <F> - The type of the first input object.
	 * @param <S> - The type of the second input object.
	 * @param query - The query used for execution. Probably contains some '?' characters for parameters.
	 * @param converter - The converter used to convert the {@literal <F>} and {@literal <S>} input objects to an array of parameters.
	 * @return Returns a {@link BiPreparedQuery} ready to be used.
	 */
	<F, S> BiPreparedQuery<F, S> prepareQuery(Query query, BiFunction<F, S, Tuple<Object>> converter);

	/**
	 * Creates a {@link TriPreparedQuery} object for sending parameterized SQL statements to the database.
	 * This time with three acceptable input objects.
	 *
	 * <p>Read {@link #prepareQuery(Query, Function)} for further information on how to use a {@link PreparedQuery}.
	 * A {@link TriPreparedQuery} can be executed the same, but is able to accept three input objects.</p>
	 *
	 * @param <F> - The type of the first input object.
	 * @param <S> - The type of the second input object.
	 * @param <T> - The type of the third input object.
	 * @param query - The query used for execution. Probably contains some '?' characters for parameters.
	 * @param converter - The converter used to convert the {@literal <F>}, {@literal <S>} and {@literal <T>} input objects to an array of parameters.
	 * @return Returns a {@link TriPreparedQuery} ready to be used.
	 */
	<F, S, T> TriPreparedQuery<F, S, T> prepareQuery(Query query, TriFunction<F, S, T, Tuple<Object>> converter);

	/**
	 * Tries to execute the specified query. If the query contains a 'SELECT' keyword a {@link ResultSet} can be expected in the
	 * returned {@link QueryResult}. If not the {@link QueryResult} can still contain some useful information like {@link QueryResult#getAffectedRows()}
	 * or stuff for exception handling.
	 *
	 * @param query - The query to execute
	 * @return Returns a {@link QueryResult} providing information about the execution of the query. Possibly containing a {@link ResultSet}.
	 */
	QueryResult execute(Query query);

	/**
	 * Tries to execute the specified query <b>on a seperate thread</b>. If the query contains a 'SELECT' keyword a {@link ResultSet} can be expected in the
	 * returned {@link QueryResult}. If not the {@link QueryResult} can still contain some useful information like {@link QueryResult#getAffectedRows()}
	 * or stuff for exception handling.
	 *
	 * @param query - The query to execute
	 * @return Returns a {@link Scheduled} instance wrapping the incoming {@link QueryResult}. To handle the result, the {@link Scheduled#addListener(Consumer)} has to be
	 * called.
	 */
	Scheduled<QueryResult> executeAsync(Query query);

	// --- The builder ---

	/**
	 * Creates a new {@link Database.Builder} instance that can be used for connecting to a database.
	 *
	 * @return A new {@link Database.Builder}
	 */
	public static Database.Builder builder() { return new Database.Builder(); }

	/** A {@link de.craftolution.craftolibrary.Builder Builder} used to build {@link Database} instances. */
	static class Builder implements de.craftolution.craftolibrary.Builder<Database> {

		public enum DatabaseType {
			MYSQL;
		}

		@Nullable private transient String username;
		@Nullable private transient String password;
		@Nullable private transient String databaseName;
		@Nullable private transient String port = "3306";
		@Nullable private transient String hostname = "localhost";
		@Nullable private DatabaseType type = DatabaseType.MYSQL;

		@Nullable private Consumer<Exception> exceptionHandler;
		@Nullable private Consumer<Query> queryHandler;
		@Nullable private Consumer<String> logHandler;
		private boolean recordStatistics = false;

		Builder() { }

		/** TODO: Documentation */
		public Builder username(final String username) { this.username = username; return this; }

		/** TODO: Documentation */
		public Builder password(final String password) { this.password = password; return this; }

		/** TODO: Documentation */
		public Builder databaseName(final String databaseName) { this.databaseName = databaseName; return this; }

		/** TODO: Documentation */
		public Builder port(final String port) { this.port = port; return this; }

		/** TODO: Documentation */
		public Builder port(final int port) { this.port = String.valueOf(port); return this; }

		/** TODO: Documentation */
		public Builder hostname(final String hostname) { this.hostname = hostname; return this; }

		/** TODO: Documentation */
		public Builder type(final DatabaseType type) { this.type = type; return this; }

		/** TODO: Documentation */
		public Builder onException(final Consumer<Exception> exceptionHandler) { this.exceptionHandler = exceptionHandler; return this; }

		/** TODO: Documentation */
		public Builder onQuery(final Consumer<Query> queryHandler) { this.queryHandler = queryHandler; return this; }

		/** TODO: Documentation */
		public Builder onLogMessage(final Consumer<String> logHandler) { this.logHandler = logHandler; return this; }

		/** TODO: Documentation */
		public Builder recordStatistics(final boolean recordStatistics) { this.recordStatistics = recordStatistics; return this; }

		/** TODO: Documentation */
		@Override
		public Database build() throws IllegalStateException {
			if (!this.isValid(this.username)) { throw new IllegalStateException("The username isn't valid."); }
			if (!this.isValid(this.databaseName)) { throw new IllegalStateException("The databaseName isn't valid."); }
			if (!this.isValid(this.password)) { throw new IllegalStateException("The password isn't valid."); }
			if (!this.isValid(this.port)) { throw new IllegalStateException("The port isn't valid."); }
			if (!this.isValid(this.hostname)) { throw new IllegalStateException("The hostname isn't valid."); }
			if (this.type == null) { throw new IllegalStateException("The type isn't valid."); }

			switch (this.type) {
				case MYSQL:
				default: return new MySQL(this.username, this.password, this.databaseName, this.port, this.hostname, this.exceptionHandler, this.queryHandler, this.logHandler, this.recordStatistics);
			}
		}

		private boolean isValid(final String string) {
			return string != null && !string.isEmpty() && string.replace(" ", "").length() > 1;
		}
	}

}
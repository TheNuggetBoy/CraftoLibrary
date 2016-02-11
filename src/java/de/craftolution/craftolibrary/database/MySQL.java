package de.craftolution.craftolibrary.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.Tokens;
import de.craftolution.craftolibrary.TriFunction;
import de.craftolution.craftolibrary.database.query.BiPreparedQuery;
import de.craftolution.craftolibrary.database.query.PreparedQuery;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.query.TriPreparedQuery;
import de.craftolution.craftolibrary.database.table.Table;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 08.02.2016
 */
public class MySQL implements Database {

	MySQL(final String username, final String dbName, final String password, final String port, final String hostname, final Consumer<Exception> exceptionHandler, final Consumer<Query> queryHandler) throws SQLException, ClassNotFoundException {

	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Result<Statement> createTable(final Table table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Statement> createStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.craftolution.craftolibrary.database.Database#prepareStatement(de.craftolution.craftolibrary.database.query.Query)
	 */
	@Override
	public Result<PreparedStatement> prepareStatement(final Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.craftolution.craftolibrary.database.Database#prepareQuery(de.craftolution.craftolibrary.database.query.Query, java.util.function.Function)
	 */
	@Override
	public <F> PreparedQuery<F> prepareQuery(final Query query, final Function<F, Tokens<Object>> converter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.craftolution.craftolibrary.database.Database#prepareQuery(de.craftolution.craftolibrary.database.query.Query, java.util.function.BiFunction)
	 */
	@Override
	public <F, S> BiPreparedQuery<F, S> prepareQuery(final Query query, final BiFunction<F, S, Tokens<Object>> converter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.craftolution.craftolibrary.database.Database#prepareQuery(de.craftolution.craftolibrary.database.query.Query, de.craftolution.craftolibrary.TriFunction)
	 */
	@Override
	public <F, S, T> TriPreparedQuery<F, S, T> prepareQuery(final Query query,
			final TriFunction<F, S, T, Tokens<Object>> converter) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.craftolution.craftolibrary.database.Database#execute(de.craftolution.craftolibrary.database.query.Query)
	 */
	@Override
	public QueryResult execute(final Query query) {
		// TODO Auto-generated method stub
		return null;
	}

}
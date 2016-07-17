package de.craftolution.craftolibrary.database.query;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.function.Consumer;

import de.craftolution.craftolibrary.Tuple;
import de.craftolution.craftolibrary.database.Database;
import de.craftolution.craftolibrary.database.QueryResult;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 17.07.2016
 */
public class PreparedQueryResult<Input> extends QueryResult {

	private static final long serialVersionUID = -7959775427522218562L;

	private final Input input;
	private final Tuple<Object> convertedInput;
	private final boolean executedManually;
	private final PreparedQuery<Input> preparedQuery;

	/** TODO: Documentation */
	public PreparedQueryResult(Database database, Query query, int affectedRows, Statement statement, ResultSet result, Exception exception, Consumer<Exception> exceptionReporter, 
			Duration executionDuration, Input input, Tuple<Object> convertedInput, boolean executedManually, PreparedQuery<Input> preparedQuery) {
		super(database, query, affectedRows, statement, result, exception, exceptionReporter, executionDuration);
		this.input = input;
		this.convertedInput = convertedInput;
		this.executedManually = executedManually;
		this.preparedQuery = preparedQuery;
	}

	/** TODO: Documentation */
	public Input getInput() { return this.input; }

	/** TODO: Documentation */
	public Tuple<Object> getConvertedInput() { return this.convertedInput; }

	/** TODO: Documentation */
	public boolean wasExecutedManually() { return this.executedManually; }

	/** TODO: Documentation */
	public PreparedQuery<Input> getPreparedQuery() { return this.preparedQuery; }

	/** TODO: Documentation */
	public Query buildQuery() { return this.getPreparedQuery().buildQuery(this.getInput()); }

}
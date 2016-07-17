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
@Deprecated
public class BiPreparedQueryResult<FirstInput, SecondInput> extends QueryResult {

	private static final long serialVersionUID = -7959775427522218562L;

	private final FirstInput firstInput;
	private final SecondInput secondInput;
	private final Tuple<Object> convertedInput;
	private final boolean executedNormally;
	private final BiPreparedQuery<FirstInput, SecondInput> preparedQuery;

	/** TODO: Documentation */
	public BiPreparedQueryResult(Database database, Query query, int affectedRows, Statement statement, ResultSet result, Exception exception, Consumer<Exception> exceptionReporter, 
			Duration executionDuration, FirstInput firstInput, SecondInput secondInput, Tuple<Object> convertedInput, boolean executedNormally, BiPreparedQuery<FirstInput, SecondInput> preparedQuery) {
		super(database, query, affectedRows, statement, result, exception, exceptionReporter, executionDuration);
		this.firstInput = firstInput;
		this.secondInput = secondInput;
		this.convertedInput = convertedInput;
		this.executedNormally = executedNormally;
		this.preparedQuery = preparedQuery;
	}

	/** TODO: Documentation */
	public FirstInput getFirstInput() { return this.firstInput; }

	/** TODO: Documentation */
	public SecondInput getSecondInput() { return this.secondInput; }

	/** TODO: Documentation */
	public Tuple<Object> getConvertedInput() { return this.convertedInput; }

	/** TODO: Documentation */
	public boolean wasExecutedManually() { return this.executedNormally; }

	/** TODO: Documentation */
	public BiPreparedQuery<FirstInput, SecondInput> getPreparedQuery() { return this.preparedQuery; }

	/** TODO: Documentation */
	public Query buildQuery(FirstInput firstInput, SecondInput secondInput) { return this.getPreparedQuery().buildQuery(this.getFirstInput(), this.getSecondInput()); }

}
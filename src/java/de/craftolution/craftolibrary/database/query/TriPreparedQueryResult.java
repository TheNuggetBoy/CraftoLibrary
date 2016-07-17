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
public class TriPreparedQueryResult<FirstInput, SecondInput, ThirdInput> extends QueryResult {

	private static final long serialVersionUID = -7959775427522218562L;

	private final FirstInput firstInput;
	private final SecondInput secondInput;
	private final ThirdInput thirdInput;
	private final Tuple<Object> convertedInput;
	private final boolean executedNormally;
	private final TriPreparedQuery<FirstInput, SecondInput, ThirdInput> preparedQuery;

	/** TODO: Documentation */
	public TriPreparedQueryResult(Database database, Query query, int affectedRows, Statement statement, ResultSet result, Exception exception, Consumer<Exception> exceptionReporter, 
			Duration executionDuration, FirstInput firstInput, SecondInput secondInput, ThirdInput thirdInput, Tuple<Object> convertedInput, boolean executedNormally, 
			TriPreparedQuery<FirstInput, SecondInput, ThirdInput> preparedQuery) {
		super(database, query, affectedRows, statement, result, exception, exceptionReporter, executionDuration);
		this.firstInput = firstInput;
		this.secondInput = secondInput;
		this.thirdInput = thirdInput;
		this.convertedInput = convertedInput;
		this.executedNormally = executedNormally;
		this.preparedQuery = preparedQuery;
	}

	/** TODO: Documentation */
	public FirstInput getFirstInput() { return this.firstInput; }

	/** TODO: Documentation */
	public SecondInput getSecondInput() { return this.secondInput; }

	/** TODO: Documentation */
	public ThirdInput getThirdInput() { return this.thirdInput; }

	/** TODO: Documentation */
	public Tuple<Object> getConvertedInput() { return this.convertedInput; }

	/** TODO: Documentation */
	public boolean wasExecutedManually() { return this.executedNormally; }

	/** TODO: Documentation */
	public TriPreparedQuery<FirstInput, SecondInput, ThirdInput> getPreparedQuery() { return this.preparedQuery; }

	/** TODO: Documentation */
	public Query buildQuery(FirstInput firstInput, SecondInput secondInput) { return this.getPreparedQuery().buildQuery(this.getFirstInput(), this.getSecondInput(), this.getThirdInput()); }

}
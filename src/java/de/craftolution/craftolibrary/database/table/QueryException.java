package de.craftolution.craftolibrary.database.table;

import de.craftolution.craftolibrary.database.QueryResult;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 16.08.2016
 */
public class QueryException extends RuntimeException {

	private static final long serialVersionUID = -8687645573295294162L;

	private final QueryResult result;

	/** TODO: Documentation */
	public QueryException(QueryResult result, String message) {
		super(message, result.getException().get());
		this.result = result;
	}

	/** TODO: Documentation */
	public QueryResult getQueryResult() { return this.result; }
	
}
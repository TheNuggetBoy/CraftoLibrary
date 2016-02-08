package de.craftolution.craftolibrary.database.result;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface QueryResult {

	int getAffectedRows();

	Optional<Statement> getStatement();

	Optional<ResultSet> getResultSet();

	Optional<Exception> getException();

	List<Integer> getGeneratedKeys();

	List<String> getColumns();

	List<Row> getRows();

	boolean wasSuccess();

}
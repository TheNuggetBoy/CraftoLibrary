/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import de.craftolution.craftolibrary.Check;

/**
 * Represents a query that can be executed.
 *
 * @author Fear837
 * @since 11.02.2016
 */
public interface Query extends Cloneable {

	static final HashMap<String, WeakReference<RawQuery>> queries = new HashMap<>();

	/**
	 * Creates a {@link RawQuery} just containing the specified sql code.
	 * @param rawQuery - The sql code
	 * @return Returns the final query
	 * @throws IllegalArgumentException If the specified {@code rawQuery} is empty or null.
	 */
	public static Query of(final String rawQuery) throws IllegalArgumentException {
		Check.notNullNotEmpty(rawQuery, "The rawQuery cannot be null!");
		if (Query.queries.containsKey(rawQuery)) {
			final WeakReference<RawQuery> queryRef = Query.queries.get(rawQuery);
			if (queryRef.get() != null) {
				return queryRef.get();
			}
		}

		final RawQuery newQuery = new RawQuery(rawQuery);
		Query.queries.put(rawQuery, new WeakReference<RawQuery>(newQuery));
		return newQuery;
	}

	/**
	 * Creates a {@link SelectQuery}.
	 * @param columns - The columns that will be selected.
	 * @return Returns a new {@link SelectQuery}
	 * @throws IllegalArgumentException If the specified columns array is either {@code null} or empty.
	 */
	public static SelectQuery select(final String... columns) throws IllegalArgumentException { return new SelectQuery(columns); }

	/**
	 * Creates a {@link InsertQuery}.
	 * @param table - The table which the values get inserted into.
	 * @return Returns a new {@link InsertQuery}
	 * @throws IllegalArgumentException If the specified table is either {@code null} or empty.
	 */
	public static InsertQuery insert(final String table) throws IllegalArgumentException { return new InsertQuery(table); }

	/**
	 * Creates a {@link UpdateQuery}.
	 * @param table - The table
	 * @return Returns a new {@link UpdateQuery}
	 * @throws IllegalArgumentException If the specified table is either {@code null} or empty.
	 */
	public static UpdateQuery update(final String table) throws IllegalArgumentException { return new UpdateQuery(table); }

	/**
	 * Creates a {@link RemoveQuery}
	 * @param table - The table
	 * @return Returns a new {@link RemoveQuery}
	 * @throws IllegalArgumentException If the specified table is either {@code null} or empty.
	 */
	public static RemoveQuery remove(final String table) throws IllegalArgumentException { return new RemoveQuery(table); }

	/** Escapes the given text by replacing "'" with "''". */
	public static String escape(String text) { return text.replace("'", "''").replace("\"", "\"\""); }

	/** Creates an exact copy of this query. */
	Query clone();

}
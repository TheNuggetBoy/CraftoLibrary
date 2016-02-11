package de.craftolution.craftolibrary.database.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import de.craftolution.craftolibrary.Check;

public interface Query {

	static final HashMap<String, WeakReference<RawQuery>> queries = new HashMap<>();

	/** TODO: Documentation */
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

	/** TODO: Documentation */
	public static SelectQuery select(final String... columns) throws IllegalArgumentException { return new SelectQuery(columns); }

	/** TODO: Documentation */
	public static InsertQuery insert(final String table) throws IllegalArgumentException { return new InsertQuery(table); }

	/** TODO: Documentation */
	public static UpdateQuery update(final String table) throws IllegalArgumentException { return new UpdateQuery(table); }

	/** TODO: Documentation */
	public static RemoveQuery remove(final String table) throws IllegalArgumentException { return new RemoveQuery(table); }

}
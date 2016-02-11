package de.craftolution.craftolibrary.database.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public interface Query {

	static final HashMap<String, WeakReference<RawQuery>> queries = new HashMap<>();

	/** TODO: Documentation */
	public static Query of(final String rawQuery) {
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
	public static SelectQuery select(final String... columns) { return new SelectQuery(columns); }

	/** TODO: Documentation */
	public static InsertQuery insert(final String table) { return new InsertQuery(table); }

}
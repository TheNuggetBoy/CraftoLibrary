/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.util.List;

import com.google.common.collect.Lists;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 11.02.2016
 */
public class RemoveQuery implements Query {

	private final String table;
	private final List<List<String>> whereChains = Lists.newArrayList();

	RemoveQuery(final String tableName) { this.table = Check.notNullNotEmpty(tableName, "The tableName cannot be null!"); }

	/** TODO: Documentation */
	public RemoveQuery where(final String... whereClauses) {
		Check.notNullNotEmpty(whereClauses, "The were clauses cannot be null or empty!");
		final List<String> whereChain = Lists.newArrayList();
		for (final String whereClause : whereClauses) {
			whereChain.add(Check.notNullNotEmpty(whereClause, "The whereClause cannot be null or empty!"));
		}
		this.whereChains.add(whereChain);
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder("REMOVE FROM `").append(this.table).append("`");

		if (!this.whereChains.isEmpty()) {
			b.append(" WHERE ");

			for (final List<String> whereChain : this.whereChains) {
				for (final String whereClause : whereChain) {
					b.append(whereClause).append(" AND ");
				}
				b.delete(b.length() - 5, b.length());
				b.append(" OR ");
			}
			b.delete(b.length() - 4, b.length());
		}

		return b.append(";").toString();
	}

	@Override
	public RemoveQuery clone() {
		final RemoveQuery query = new RemoveQuery(this.table);
		query.whereChains.addAll(this.whereChains);
		return query;
	}

}
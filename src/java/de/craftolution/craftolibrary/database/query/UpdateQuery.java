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
public class UpdateQuery implements Query {

	private final String table;
	private final List<String> columns = Lists.newArrayList();
	private final List<List<String>> whereChains = Lists.newArrayList();

	UpdateQuery(final String table) { this.table = Check.notNullNotEmpty(table, "The tableName cannot be null!"); }

	/** TODO: Documentation */
	public UpdateQuery columns(final String... columns) {
		Check.notNullNotEmpty(columns, "The columns cannot be null or empty!");
		for (final String column : columns) {
			this.columns.add(Check.notNullNotEmpty(column, "The column cannot be empty or null!"));
		}
		return this;
	}

	/** TODO: Documentation */
	public UpdateQuery where(final String... whereClauses) {
		Check.notNullNotEmpty(whereClauses, "The were clauses cannot be null or empty!");
		final List<String> whereChain = Lists.newArrayList();
		for (final String whereClause : whereClauses) {
			whereChain.add(Check.notNullNotEmpty(whereClause, "The whereClause cannot be null or empty!"));
		}
		this.whereChains.add(whereChain);
		return this;
	}

	@Override
	public String toString() throws IllegalStateException {
		if (this.columns.isEmpty()) { throw new IllegalStateException("No columns were specified in this updatequery!"); }

		final StringBuilder b = new StringBuilder().append("UPDATE `").append(this.table).append("` SET ");
		for (final String column : this.columns) {
			b.append(column).append(", ");
		}
		b.delete(b.length() - 2, b.length());

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
	public UpdateQuery clone() {
		UpdateQuery query = new UpdateQuery(this.table);
		query.columns.addAll(this.columns);
		
		for (List<String> whereChain : this.whereChains) {
			List<String> newWhereChain = Lists.newArrayList();
			newWhereChain.addAll(whereChain);
			query.whereChains.add(newWhereChain);
		}
		
		return query;
	}

}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

import java.util.Arrays;

import de.craftolution.craftolibrary.StringUtils;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class SelectQuery implements Query {

	private final String[] columns;
	private final boolean selectAll;

	private String fromTable;
	private String[] whereClauses;

	private String orderByColumn;
	private Order orderByOrder;

	private int firstLimit = Integer.MIN_VALUE;
	private int secondLimit = Integer.MIN_VALUE;

	SelectQuery(final String... columns) {
		this.columns = columns;
		if (this.columns.length == 1 && columns[0].equals("*")) { this.selectAll = true; }
		else { this.selectAll = false; }
	}

	/** TODO: Documentation */
	public SelectQuery from(final String table) { this.fromTable = table; return this; }

	/** TODO: Documentation */
	public SelectQuery where(final String... whereClauses) { this.whereClauses =  whereClauses; return this; }

	/** TODO: Documentation */
	public SelectQuery orderBy(final String column, final Order order) { this.orderByColumn = column; this.orderByOrder = order; return this; }

	/** TODO: Documentation */
	public SelectQuery limit(final int limit) { this.firstLimit = limit; return this; }

	/** TODO: Documentation */
	public SelectQuery limit(final int min, final int max) { this.firstLimit = min; this.secondLimit = max; return this; }

	@Override
	public String toString() throws IllegalStateException {
		if (this.columns == null) { throw new IllegalStateException("No columns were specified. (null)"); }
		else if (this.columns.length < 1) { throw new IllegalStateException("No columns were specified. (empty array)"); }
		else if (this.fromTable == null) { throw new IllegalStateException("No table to select from was specified. (null)"); }
		else if (StringUtils.isReallyEmpty(this.fromTable)) { throw new IllegalStateException("No table to select from was specified. (empty)"); }

		final StringBuilder b = new StringBuilder("SELECT ");

		// Columns
		if (!this.selectAll) {
			for (int i = 0; i < this.columns.length; i++) {
				if (i != 0) { b.append(", "); }
				b.append('`').append(this.columns[i]).append('`');
			}
		}
		else { b.append("*"); }

		// From
		b.append(" FROM `").append(this.fromTable).append('`');

		// Where
		if (this.whereClauses != null && this.whereClauses.length > 0) {
			b.append(" WHERE ");
			for (int i = 0; i < this.whereClauses.length; i++) {
				if (i != 0) { b.append(" AND "); }
				b.append(this.whereClauses[i]);
			}
		}

		// Order by
		if (this.orderByColumn != null && this.orderByOrder != null) {
			b.append(" ORDER BY `").append(this.orderByColumn).append("` ").append(this.orderByOrder.toString());
		}

		// Limit
		if (this.firstLimit != Integer.MIN_VALUE) {
			b.append(" LIMIT");
			if (this.secondLimit == Integer.MIN_VALUE) {
				b.append(' ').append(this.firstLimit);
			}
			else { b.append('(').append(this.firstLimit).append(',').append(this.secondLimit).append(')'); }
		}

		return b.append(";").toString();
	}

	@Override
	public SelectQuery clone() {
		final SelectQuery query = new SelectQuery(this.columns);
		query.fromTable = this.fromTable;
		query.whereClauses = Arrays.copyOf(this.whereClauses, this.whereClauses.length);
		query.orderByColumn = this.orderByColumn;
		query.orderByOrder = this.orderByOrder;
		query.firstLimit = this.firstLimit;
		query.secondLimit = this.secondLimit;
		return query;
	}

}
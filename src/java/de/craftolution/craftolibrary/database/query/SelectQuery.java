package de.craftolution.craftolibrary.database.query;

import de.craftolution.craftolibrary.StringUtils;

public class SelectQuery implements Query {

	private final String[] columns;
	private boolean selectAll;

	private String fromTable;
	private String[] whereClauses;

	private String orderByColumn;
	private Order orderByOrder;

	private int firstLimit = Integer.MIN_VALUE;
	private int secondLimit = Integer.MIN_VALUE;

	SelectQuery(final String... columns) {
		this.columns = columns;
		if (this.columns.length == 1 && columns[0].equals("*")) { this.selectAll = true; }
	}

	public SelectQuery from(final String table) { this.fromTable = table; return this; }

	public SelectQuery where(final String... whereClauses) { this.whereClauses =  whereClauses; return this; }

	public SelectQuery orderBy(final String column, final Order order) { this.orderByColumn = column; this.orderByOrder = order; return this; }

	public SelectQuery limit(final int limit) { this.firstLimit = limit; return this; }

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

}
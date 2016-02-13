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
 * @author Kevin
 * @since 11.02.2016
 */
public class InsertQuery implements Query {

	private final String table;
	private final List<String> columns = Lists.newArrayList();
	private final List<String> values = Lists.newArrayList();
	private final List<String[]> pairedInserts = Lists.newArrayList();
	private final List<String> onDuplicateKey = Lists.newArrayList();

	InsertQuery(final String tableName) { this.table = Check.notNullNotEmpty(tableName, "The tableName cannot be null or empty!"); }

	/** TODO: Documentation */
	public InsertQuery columns(final String... columns) throws IllegalArgumentException {
		Check.notNullNotEmpty(columns, "The columns array cannot be null or empty!");
		for (final String column : columns) { this.columns.add(Check.notNullNotEmpty(column, "The column cannot be null or empty!")); }
		return this;
	}

	/** TODO: Documentation */
	public InsertQuery values(final String... values) {
		Check.notNullNotEmpty(values, "The value array cannot be null or empty!");
		for (final String value : values) { this.values.add(Check.notNullNotEmpty(value, "The value cannot be null or empty!")); }
		return this;
	}

	/** TODO: Documentation */
	public InsertQuery insert(final String column, final String value) {
		Check.notNullNotEmpty(column, "The column cannot be null or empty!");
		Check.notNullNotEmpty(value, "The value cannot be null or empty!");
		this.pairedInserts.add(new String[] { column, value });
		return this;
	}

	/** TODO: Documentation */
	public InsertQuery onDuplicateKey(final String... values) {
		Check.notNullNotEmpty(values, "The values array cannot be null!");
		for (final String value : values) { this.onDuplicateKey.add(Check.notNullNotEmpty(value, "The value cannot be null or empty!")); }
		return this;
	}

	@Override
	public String toString() throws IllegalStateException {
		if (!this.columns.isEmpty()) {
			if (this.values.isEmpty()) { throw new IllegalStateException("No values were specified but columns were?!"); }
		}
		if (!this.values.isEmpty()) {
			if (this.columns.isEmpty()) { throw new IllegalStateException("No columns were specified but values were?!"); }
		}
		if (this.columns.isEmpty() && this.values.isEmpty() && this.pairedInserts.isEmpty()) {
			throw new IllegalStateException("No columns or values were specified.");
		}

		final StringBuilder b = new StringBuilder().append("INSERT INTO `").append(this.table).append("` ");
		final StringBuilder columns = new StringBuilder("(");
		final StringBuilder values = new StringBuilder (" VALUES (");

		for (final String[] pairedInsert : this.pairedInserts) {
			columns.append('`').append(pairedInsert[0]).append("`, ");

			if (Check.isDouble(pairedInsert[1])) { values.append(pairedInsert[1]).append(", "); }
			else { values.append("'").append(pairedInsert[1]).append("', "); }
		}

		for (final String column : this.columns) {
			columns.append('`').append(column).append("`, ");
		}

		for (final String value : this.values) {
			if (Check.isDouble(value)) { values.append(value).append(", "); }
			else { values.append("'").append(value).append("', "); }
		}

		columns.delete(columns.length() - 2, columns.length()).append(')');
		values.delete(values.length() - 2, values.length()).append(')');
		b.append(columns).append(values);

		if (!this.onDuplicateKey.isEmpty()) {
			b.append(" ON DUPLICATE KEY UPDATE ");
			for (final String value : this.onDuplicateKey) {
				b.append(value).append(", ");
			}

			b.delete(b.length() - 2, b.length());
		}

		b.append(';');

		return b.toString();
	}

	@Override
	public InsertQuery clone() {
		InsertQuery query = new InsertQuery(this.table);
		query.columns.addAll(this.columns);
		query.values.addAll(this.values);
		query.pairedInserts.addAll(this.pairedInserts);
		query.onDuplicateKey.addAll(this.onDuplicateKey);
		return query;
	}

}
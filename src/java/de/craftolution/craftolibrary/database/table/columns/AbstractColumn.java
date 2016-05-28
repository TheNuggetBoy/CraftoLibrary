/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.StringUtils;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.table.Column;
import de.craftolution.craftolibrary.database.table.DataType;
import de.craftolution.craftolibrary.database.table.IndexType;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 * @param <V> - The type of value this column contains
 * @param <T> - The implementing column class
 */
public abstract class AbstractColumn<V, T> implements Serializable, Column {

	private static final long serialVersionUID = -4740955053761415509L;

	@Nullable private String name;
	@Nullable private String comment;
	@Nullable private IndexType index;
	@Nullable private boolean nullable;
	@Nullable private V standardValue;

	protected abstract T instance();

	@Override
	public String getName() { return this.name; }

	@Override
	public Optional<String> getComment() { return Optional.ofNullable(this.comment); }

	@Override
	public boolean isNullable() { return this.nullable; }

	/** TODO: Documentation */
	public T name(final String columnName) {
		this.name = columnName;
		return this.instance();
	}

	/** TODO: Documentation */
	public T comment(final String columnComment) {
		this.comment = columnComment;
		return this.instance();
	}

	/** TODO: Documentation */
	public T index(final IndexType index) {
		this.index = index;
		return this.instance();
	}

	/** TODO: Documentation */
	public T nullable() {
		this.nullable = true;
		return this.instance();
	}

	/** TODO: Documentation */
	public T standard(final V standardValue) {
		this.standardValue = standardValue;
		return this.instance();
	}

	@Override
	public Query toQuery() throws IllegalStateException {
		// <NAME> [TYPE] [LENGTH] <NOT NULL | NULL> <DEFAULT> [AUTO_INCREMENT] <index> <COMMENT>
		if (this.name == null || StringUtils.isReallyEmpty(this.name)) { throw new IllegalStateException("The column name is null!"); }

		final ColumnDefinitionBuilder definitions = this.define(new ColumnDefinitionBuilder());
		if (definitions.type == null) {
			definitions.type = DataType.valueOf(this.getClass()).orElseThrow(() -> new IllegalStateException("The column data type for column '" + this.getName() + "' was not specified."));
		}

		final StringBuilder b = new StringBuilder();
		b.append('`').append(this.name.replace("`", "")).append('`').append(' ');
		b.append(definitions.type.name());

		if (definitions.enumValues != null && definitions.enumValues.length > 0) { // If its a enum
			b.append('(');
			for (final String constant : definitions.enumValues) { b.append('\'').append(constant).append("', "); }
			b.delete(b.length() - 2, b.length()).append(')');
		}
		else if (definitions.firstLength != null) { // Append length otherwise
			b.append('(').append(definitions.firstLength);

			if (definitions.secondLength != null) {
				b.append(", ").append(definitions.secondLength);
			}
			b.append(')');
		}

		b.append(' ');

		if (this.nullable) { b.append("NULL "); }
		else { b.append("NOT NULL "); }

		if (this.standardValue != null) {
			b.append("DEFAULT ");

			final String standard = String.valueOf(this.standardValue);
			if (standard.equals("CURRENT_TIMESTAMP") || Check.isDouble(standard)) {
				b.append(standard.replace("'", ""));
			}
			else { b.append('\'').append(standard.replace("'", "")).append('\''); }

			b.append(' ');
		}

		if (definitions.onUpdate != null) { b.append("ON UPDATE ").append(definitions.onUpdate).append(' '); }

		if (definitions.unsigned) { b.append("UNSIGNED "); }

		if (definitions.autoIncrement) { b.append("AUTO_INCREMENT "); }

		if (this.index != null) { b.append(this.index.toString()).append(' '); }

		if (this.comment != null) { b.append("COMMENT '").append(this.comment.replace("'", "")).append('\''); }

		return Query.of(b.toString().trim());
	}

	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) { return builder; }

}
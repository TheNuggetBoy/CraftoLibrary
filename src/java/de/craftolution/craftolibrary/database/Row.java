/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.TimeUtils;

/**
 * Represents a single row in a {@link ResultSet}.
 *
 * @author Fear837
 * @since 12.02.2016
 */
public class Row implements Serializable {

	private static final long serialVersionUID = 159668534154443560L;

	private final QueryResult result;
	private final int rowIndex;
	private final Map<String, Object> values = Maps.newHashMap();
	private final Set<String> columns = Sets.newHashSet();

	Row(final QueryResult result, final int rowIndex) throws SQLException {
		this.result = result;
		this.rowIndex = rowIndex;

		for (final String column : result.getColumns()) {
			this.columns.add(column);
			
			final Object obj = result.getResultSet().get().getObject(column);
			if (obj == null) { this.values.put(column.toLowerCase(), Void.TYPE); }
			else { this.values.put(column.toLowerCase(), obj); }
		}
	}

	@Nullable
	private Object pull(final String columnLabel) throws NoSuchElementException {
		if (!this.columns.contains(Check.notNullNotEmpty(columnLabel, "The columnLabel cannot be null or empty!"))) {
			throw new NoSuchElementException("The column '" + columnLabel + "' doesnt exist at row index '" + this.rowIndex + "'. [Query: " + this.result.getQuery().toString() + "]");
		}
		final Object value = this.values.get(columnLabel);
		return value.equals(Void.TYPE) ? null : value;
	}

	/** @return Returns the index of this row in the overall {@link ResultSet}. */
	public int index() { return this.rowIndex; }

	// --- Primitive getters ---

	/**
	 * Retrieves the value of the designated column in the current row as a {@code String} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 */
	@Nullable public String getString(final String columnLabel) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		return value != null ? value.toString() : null;
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code String} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the given {@code defaultValue} is returned.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 */
	@Nonnull public String getString(final String columnLabel, String defaultValue) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		return value != null ? value.toString() : defaultValue;
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Integer} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an integer.
	 */
	@Nullable public Integer getInt(final String columnLabel) throws IllegalArgumentException, NoSuchElementException, NumberFormatException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Integer) { return (Integer) value; }
		else { return Integer.parseInt(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Integer} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an integer.
	 */
	@Nonnull public Integer getInt(final String columnLabel, Integer defaultValue) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Integer) { return (Integer) value; }
		else { return Integer.parseInt(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Double} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nullable public Double getDouble(final String columnLabel) throws IllegalArgumentException, NoSuchElementException, NumberFormatException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Double) { return (Double) value; }
		else { return Double.parseDouble(value.toString()); }
	}
	
	/**
	 * Retrieves the value of the designated column in the current row as a {@code Double} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nonnull public Double getDouble(final String columnLabel, Double defaultValue) throws IllegalArgumentException, NoSuchElementException, NumberFormatException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Double) { return (Double) value; }
		else { return Double.parseDouble(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Float} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nullable public Float getFloat(final String columnLabel) throws IllegalArgumentException, NoSuchElementException, NumberFormatException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Float) { return (Float) value; }
		else { return Float.parseFloat(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Float} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nonnull public Float getFloat(final String columnLabel, Float defaultValue) throws IllegalArgumentException, NoSuchElementException, NumberFormatException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Float) { return (Float) value; }
		else { return Float.parseFloat(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Boolean} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nullable public Boolean getBoolean(final String columnLabel) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Boolean) { return (Boolean) value; }
		else {
			final String valueAsString = value.toString().toLowerCase();
			return valueAsString.equals("1") || valueAsString.equals("true") || valueAsString.equals("yes");
		}
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Boolean} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 * @throws NumberFormatException If the value of the column couldn't be parsed to an double.
	 */
	@Nonnull public Boolean getBoolean(final String columnLabel, Boolean defaultValue) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Boolean) { return (Boolean) value; }
		else {
			final String valueAsString = value.toString().toLowerCase();
			return valueAsString.equals("1") || valueAsString.equals("true") || valueAsString.equals("yes");
		}
	}

	// --- SQL specific getters ---

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Timestamp} in the Java programming language.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 */
	@Nullable public Timestamp getTimestamp(final String columnLabel) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Timestamp) { return (Timestamp) value; }
		else { return Timestamp.valueOf(value.toString()); }
	}

	/**
	 * Retrieves the value of the designated column in the current row as a {@code Timestamp} in the Java programming language.
	 * If the value is not present, the given {@code defaultValue} is returned.
	 * @param columnLabel - The label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the name of the column.
	 * @return Returns the column value; if the value is {@code SQL NULL}, the value returned is null.
	 * @throws IllegalArgumentException If the specified columnLabel was either {@code null} or empty.
	 * @throws NoSuchElementException If no column exists that corresponds to the specified name/label.
	 */
	@Nonnull public Timestamp getTimestamp(final String columnLabel, Timestamp defaultValue) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Timestamp) { return (Timestamp) value; }
		else { return Timestamp.valueOf(value.toString()); }
	}

	/** TODO: Documentation */
	@Nullable public Instant getInstant(final String columnLabel) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return null; }
		if (value instanceof Timestamp) { return TimeUtils.toInstant((Timestamp) value); }
		else { return TimeUtils.toInstant(Timestamp.valueOf(value.toString())); }
	}

	/** TODO: Documentation */
	@Nonnull public Instant getInstant(final String columnLabel, Instant defaultValue) throws IllegalArgumentException, NoSuchElementException {
		final Object value = this.pull(columnLabel);
		if (value == null) { return defaultValue; }
		if (value instanceof Timestamp) { return TimeUtils.toInstant((Timestamp) value); }
		else { return TimeUtils.toInstant(Timestamp.valueOf(value.toString())); }
	}

}
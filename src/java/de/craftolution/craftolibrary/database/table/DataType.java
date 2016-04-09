/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.database.table.columns.BooleanColumn;
import de.craftolution.craftolibrary.database.table.columns.CharColumn;
import de.craftolution.craftolibrary.database.table.columns.DecimalColumn;
import de.craftolution.craftolibrary.database.table.columns.DoubleColumn;
import de.craftolution.craftolibrary.database.table.columns.EnumColumn;
import de.craftolution.craftolibrary.database.table.columns.FloatColumn;
import de.craftolution.craftolibrary.database.table.columns.IntColumn;
import de.craftolution.craftolibrary.database.table.columns.TextColumn;
import de.craftolution.craftolibrary.database.table.columns.TimestampColumn;
import de.craftolution.craftolibrary.database.table.columns.VarCharColumn;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 18.12.2015
 */
public enum DataType {

	BIT (null),

	TINYINT (IntColumn.class),

	BOOLEAN (BooleanColumn.class),

	SMALLINT (IntColumn.class),

	MEDIUMINT (IntColumn.class),

	INT (IntColumn.class),

	BIGINT (IntColumn.class),

	DECIMAL (DecimalColumn.class),

	FLOAT (FloatColumn.class),

	DOUBLE (DoubleColumn.class),

	DATE (null),

	DATETIME (null),

	TIMESTAMP (TimestampColumn.class),

	TIME (null),

	YEAR (null),

	BINARY (null),

	VARBINARY (null),

	TINYBLOB (null),

	TINYTEXT (null),

	BLOB (null),

	CHAR (CharColumn.class),

	VARCHAR (VarCharColumn.class),

	TEXT (TextColumn.class),

	MEDIUMBLOB (null),

	MEDIUMTEXT (null),

	LONGBLOB (null),

	LONGTEXT (null),

	ENUM (EnumColumn.class),

	SET (null),

	;

	@Nullable private final Class<? extends Column> columnClazz;

	DataType (@Nullable final Class<? extends Column> columnClazz) {
		this.columnClazz = columnClazz;
	}

	public Optional<Class<? extends Column>> getColumnClass() { return Optional.ofNullable(this.columnClazz); }

	// --- Static accessors ---

	private static final HashMap<Class<? extends Column>, DataType> columnClazzMap = new HashMap<>();

	static {
		for (final DataType dataType : DataType.values()) {
			if (dataType.getColumnClass().isPresent()) {
				DataType.columnClazzMap.put(dataType.getColumnClass().get(), dataType);
			}
		}
	}

	/**
	 * Looks for the {@link DataType} that supports the specified {@link Column}.
	 * Keep in mind that multiple datatypes could support the same column class (like IntColumn for example).
	 * @param columnClazz - The class of the column
	 * @return Returns a {@link DataType} if found, otherwise {@link Optional#empty()}.
	 */
	public static Optional<DataType> valueOf(final Class<? extends Column> columnClazz) {
		return Optional.ofNullable(DataType.columnClazzMap.get(columnClazz));
	}
}
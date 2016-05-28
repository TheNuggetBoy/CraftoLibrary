package de.craftolution.craftolibrary.database.table;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.craftolution.craftolibrary.database.table.attributes.AutoIncrementable;
import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;
import de.craftolution.craftolibrary.database.table.attributes.Lengthable;
import de.craftolution.craftolibrary.database.table.attributes.Unsignable;
import de.craftolution.craftolibrary.database.table.columns.AbstractColumn;
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
import de.craftolution.craftolibrary.database.table.object.TableColumn;
import de.craftolution.craftolibrary.database.table.object.TableIndex;

public class ObjectTableColumnResolver {

	public static List<Column> resolve(Field field) throws InstantiationException, IllegalAccessException, IllegalArgumentException {
		if (!field.isAnnotationPresent(TableColumn.class)) { return Collections.emptyList(); }
		final List<Column> columnList = new ArrayList<>(1);

		TableColumn annotation = field.getAnnotation(TableColumn.class);
		DataType type = annotation.type();
		final String name = annotation.name().isEmpty() ? field.getName() : annotation.name();
		final int length = annotation.length();
		final int precision = annotation.precision();
		final String comment = annotation.comment().isEmpty() ? null : annotation.comment();
		final boolean nullable = annotation.nullable();
		final boolean unsigned = annotation.unsigned();
		final boolean autoIncrement = annotation.autoIncrement();
		final String onUpdate = annotation.onUpdate().isEmpty() ? null : annotation.onUpdate(); // TODO
		final String standard = annotation.standard().isEmpty() ? null : annotation.standard();
		final String[] values = annotation.values().length == 0 ? null : annotation.values();   // TODO

		AbstractColumn<?, ?> instance = null;

		if (type.equals(DataType.UNKNOWN)) {
			// Multiple column types:
			if (field.getType().equals(int.class) || field.getType().equals(Integer.class))
			{ type = DataType.INT; }
			else if (field.getType().equals(short.class) || field.getType().equals(Short.class))
			{ type = DataType.SMALLINT; }
			else if (field.getType().equals(long.class) || field.getType().equals(Long.class))
			{ type = DataType.BIGINT; }
			else if (field.getType().equals(double.class) || field.getType().equals(Double.class))
			{ type = DataType.DOUBLE; }
			else if (field.getType().equals(float.class) || field.getType().equals(Float.class))
			{ type = DataType.FLOAT; }
			else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class))
			{ type = DataType.BOOLEAN; }
			else if (field.getType().equals(char.class) || field.getType().equals(Character.class))
			{ type = DataType.CHAR; instance = new CharColumn().length(1); }
			else if (field.getType().equals(String.class))
			{ type = DataType.VARCHAR; }
			else if (field.getType().equals(Duration.class))
			{ type = DataType.BIGINT; instance = new IntColumn().comment("Duration in millis."); }
			else if (field.getType().equals(Instant.class) || field.getType().equals(Timestamp.class))
			{ type = DataType.TIMESTAMP; }
			else if (field.getType().equals(LocalDate.class))
			{ type = DataType.DATE; }
			else if (field.getType().equals(LocalDateTime.class))
			{ type = DataType.DATETIME; }
		}

		if (instance == null) {
			if (!type.getColumnClass().isPresent()) { 
				throw new IllegalArgumentException("The specified datatype is not supported."); 
			}
			instance = (AbstractColumn<?, ?>) type.getColumnClass().get().newInstance();
		}

		instance.name(name);
		if (comment != null) { instance.comment( comment ); }
		if (nullable) { instance.nullable(); }
		if (length != Integer.MIN_VALUE && instance instanceof Lengthable) { ((Lengthable<?>) instance).length( length ); }
		if (precision != Integer.MIN_VALUE && instance instanceof DoubleLenghtable) { ((DoubleLenghtable<?>) instance).length(length, precision); }
		if (autoIncrement && instance instanceof AutoIncrementable) { ((AutoIncrementable<?>) instance).autoIncrement(); }
		if (unsigned && instance instanceof Unsignable) { ((Unsignable<?>) instance).unsigned(); }

		switch (type) {
			case TINYINT:
			case SMALLINT:
			case MEDIUMINT:
			case INT:
			case BIGINT:
				IntColumn column = (IntColumn) instance;
				if (annotation.standard() != null) { column.standard( Integer.parseInt(annotation.standard()) ); }
				break;
			case UNKNOWN:
			case BINARY:
			case VARBINARY:
			case BIT:
			case DATE:
			case DATETIME:
			case SET:
			case TINYBLOB:
			case MEDIUMBLOB:
			case BLOB:
			case LONGBLOB:
			case YEAR:
			case TIME:
				throw new IllegalStateException("Unsupported datatype: " + type);
			case TINYTEXT:
			case MEDIUMTEXT:
			case TEXT:
			case LONGTEXT:
				TextColumn textColumn = (TextColumn) instance;
				if (!standard.isEmpty()) { textColumn.standard( standard ); }
				break;
			case CHAR:
				CharColumn charColumn = (CharColumn) instance;
				if (!standard.isEmpty()) { charColumn.standard( standard ); }
			case VARCHAR:
				VarCharColumn varcharColumn = (VarCharColumn) instance;
				if (!standard.isEmpty()) { varcharColumn.standard( standard ); }
				break;
			case DECIMAL:
				DecimalColumn decimalColumn = (DecimalColumn) instance;
				if (!standard.isEmpty()) { decimalColumn.standard( Double.parseDouble(standard) ); }
			case DOUBLE:
				DoubleColumn doubleColumn = (DoubleColumn) instance;
				if (!standard.isEmpty()) { doubleColumn.standard( Double.parseDouble(standard) ); }
			case FLOAT:
				FloatColumn floatColumn = (FloatColumn) instance;
				if (!standard.isEmpty()) { floatColumn.standard( Float.parseFloat(standard) ); }
				break;
			case BOOLEAN:
				BooleanColumn boolColumn = (BooleanColumn) instance;
				if (!standard.isEmpty()) { boolColumn.standard( Boolean.parseBoolean(standard) ); }
				break;
			case ENUM:
				EnumColumn enumColumn = (EnumColumn) instance;
				if (values != null) { enumColumn.values( values ); }
				else { throw new IllegalArgumentException("The specified enum tablecolumn doesnt have values."); }
				if (!standard.isEmpty()) { enumColumn.standard( standard ); }
				break;
			case TIMESTAMP:
				TimestampColumn timestampColumn = (TimestampColumn) instance;
				if (!standard.isEmpty()) { timestampColumn.standard(standard); }
				if (!onUpdate.isEmpty()) { timestampColumn.onUpdate(onUpdate); }
				break;
		}

		if (field.isAnnotationPresent(TableIndex.class)) {
			TableIndex indexAnnotation = field.getAnnotation(TableIndex.class);
			instance.index(indexAnnotation.type());
		}

		columnList.add(instance);
		return columnList;
	}

}
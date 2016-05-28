package de.craftolution.craftolibrary.database.table;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.sun.jmx.snmp.Timestamp;

import de.craftolution.craftolibrary.database.Database;
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
import de.craftolution.craftolibrary.database.table.object.Clause;
import de.craftolution.craftolibrary.database.table.object.TableColumn;
import de.craftolution.craftolibrary.database.table.object.TableIndex;
import de.craftolution.craftolibrary.database.table.object.TableMeta;

public class ObjectTable<T> extends Table {

	ObjectTable(String name, List<Column> columns, List<Index> indices, String comment, Engine engine, CharSet charset) {
		super(name, columns, indices, comment, engine, charset);
	}

	/** 
	 * TODO: Documentation 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static <T> ObjectTable<T> of(Class<T> clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String name = null;
		List<Column> columns = Lists.newArrayList();
		List<Index> indices = Lists.newArrayList();
		String comment = null;
		Engine engine = null;
		CharSet charSet = null;

		if (clazz.isAnnotationPresent(TableMeta.class)) {
			TableMeta meta = clazz.getAnnotation(TableMeta.class);
			name = meta.name();
			comment = meta.comment();
			engine = meta.engine();
			charSet = meta.charSet();
		}
		
		if (clazz.isAnnotationPresent(TableIndex.class)) {
			TableIndex[] annotations = clazz.getAnnotationsByType(TableIndex.class);
			for (int i = 0; i < annotations.length; i++) {
				TableIndex indexAnno = annotations[i];
				Index index = new Index(indexAnno.name(), indexAnno.type(), indexAnno.columns());
				indices.add(index);
			}
		}
		
		for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
			Field field = clazz.getDeclaredFields()[i];
			field.setAccessible(true);
			
			if (field.isAnnotationPresent(TableColumn.class)) {
				TableColumn columnAnno = field.getAnnotation(TableColumn.class);
				List<Column> column = constructColumn(columnAnno, field);
				columns.addAll(column);
			}
		}
		
		return new ObjectTable<T>(name, columns, indices, comment, engine, charSet);
	}
	
	private static List<Column> constructColumn(final TableColumn annotation, Field field) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		DataType type = annotation.type();
		Class<? extends Column> columnClass = type.getColumnClass().orElseThrow(() -> new ClassNotFoundException("Failed to find the column class of the datatype '" + annotation.type() + "' for the field '" + field.getName() + "'."));
		AbstractColumn<?, ?> instance = (AbstractColumn<?, ?>) columnClass.newInstance();
		List<Column> columns = Lists.newArrayList();

		if (!annotation.name().isEmpty()) { instance.name( annotation.name() ); }
		else { instance.name( field.getName() ); }

		if (type.equals(DataType.UNKNOWN)) {
			if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
				type = DataType.INT;
			}
			else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
				type = DataType.DOUBLE;
			}
			else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
				type = DataType.FLOAT;
			}
			else if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
				type = DataType.SMALLINT;
			}
			else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
				type = DataType.BIGINT;
			}
			else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
				type = DataType.BOOLEAN;
			}
			else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
				type = DataType.CHAR;
				((CharColumn) instance).length(1);
			}
			else if (field.getType().equals(String.class)) {
				type = DataType.VARCHAR;
			}
			else if (field.getType().equals(Duration.class)) {
				type = DataType.BIGINT;
				((IntColumn) instance).comment("Duration in milliseconds.");
			}
			else if (field.getType().equals(Instant.class) || field.getType().equals(Timestamp.class)) {
				type = DataType.TIMESTAMP;
			}
			else if (field.getType().equals(LocalDate.class)) {
				type = DataType.DATE;
			}
			else if (field.getType().equals(LocalDateTime.class)) {
				type = DataType.DATETIME;
			}
		}
		
		if (!annotation.comment().isEmpty()) { instance.comment( annotation.comment() ); }
		if (annotation.nullable()) { instance.nullable(); }

		if (annotation.length() != Integer.MIN_VALUE && instance instanceof Lengthable) {
			((Lengthable<?>) instance).length( annotation.length() );
		}
		
		if (annotation.precision() != Integer.MIN_VALUE && instance instanceof DoubleLenghtable) {
			((DoubleLenghtable<?>) instance).length(annotation.length(), annotation.precision());
		}

		if (annotation.autoIncrement() && instance instanceof AutoIncrementable) {
			((AutoIncrementable<?>) instance).autoIncrement();
		}

		if (annotation.unsigned() && instance instanceof Unsignable) {
			((Unsignable<?>) instance).unsigned();
		}

		// TODO: Check if field has a TableIndex annotation
		
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
				if (annotation.standard() != null) { textColumn.standard( annotation.standard() ); }
				break;
			case CHAR:
				CharColumn charColumn = (CharColumn) instance;
				if (annotation.standard() != null) { charColumn.standard( annotation.standard() ); }
			case VARCHAR:
				VarCharColumn varcharColumn = (VarCharColumn) instance;
				if (annotation.standard() != null) { varcharColumn.standard( annotation.standard() ); }
				break;
			case DECIMAL:
				DecimalColumn decimalColumn = (DecimalColumn) instance;
				if (annotation.standard() != null) { decimalColumn.standard( Double.parseDouble(annotation.standard()) ); }
			case DOUBLE:
				DoubleColumn doubleColumn = (DoubleColumn) instance;
				if (annotation.standard() != null) { doubleColumn.standard( Double.parseDouble(annotation.standard()) ); }
			case FLOAT:
				FloatColumn floatColumn = (FloatColumn) instance;
				if (annotation.standard() != null) { floatColumn.standard( Float.parseFloat(annotation.standard()) ); }
				break;
			case BOOLEAN:
				BooleanColumn boolColumn = (BooleanColumn) instance;
				if (annotation.standard() != null) { boolColumn.standard( Boolean.parseBoolean(annotation.standard()) ); }
				break;
			case ENUM:
				EnumColumn enumColumn = (EnumColumn) instance;
				if (annotation.values() != null) { enumColumn.values( annotation.values() ); }
				else { throw new IllegalArgumentException("The specified enum tablecolumn doesnt have values."); }
				if (annotation.standard() != null) { enumColumn.standard( annotation.standard() ); }
				break;
			case TIMESTAMP:
				TimestampColumn timestampColumn = (TimestampColumn) instance;
				if (annotation.standard() != null) { timestampColumn.standard(annotation.standard()); }
				if (annotation.onUpdate() != null) { timestampColumn.onUpdate(annotation.onUpdate()); }
				break;
		}

		columns.add(instance);
		return columns;
	}

	public Optional<T> selectFirst(Database database, Clause... clauses) { return null; }
	
	public List<T> select(Database database, Clause... clauses) { return null; }
	
	public Stream<T> select(Database database) { return null; }
	
	public void insert(Database database, T object) { return; }
	
	public void update(Database database, T object) { return; }
	
	public void remove(Database database, T object) { return; }
	
}
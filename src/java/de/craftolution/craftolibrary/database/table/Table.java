package de.craftolution.craftolibrary.database.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.StringUtils;
import de.craftolution.craftolibrary.database.query.Query;
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
 * @author Kevin
 * @since 08.02.2016
 */
public class Table {

	private final String name;
	private final List<Column> columns;
	private final List<Index> indices;

	@Nullable private final String comment;
	@Nullable private final Engine engine;
	@Nullable private final CharSet charset;

	Table(final String name, final List<Column> columns, final List<Index> indices, @Nullable final String comment, @Nullable final Engine engine, @Nullable final CharSet charset) {
		this.name = Check.notNullNotEmpty(name, "The name of the Table cannot be null!");
		this.columns = Check.notNullNotEmpty(columns, "The list of columns cannot be null!");
		this.indices = Check.notNull(indices, "The list of indices cannot be null!");
		this.comment = comment;
		this.engine = engine;
		this.charset = charset;
	}

	/** TODO: Documentation */
	public String getName() { return this.name; }

	/** TODO: Documentation */
	public Optional<String> getComment() { return Optional.ofNullable(this.comment); }

	/** TODO: Documentation */
	public Optional<Engine> getEngine() { return Optional.ofNullable(this.engine); }

	/** TODO: Documentation */
	public Optional<CharSet> getCharSet() { return Optional.ofNullable(this.charset); }

	/** TODO: Documentation */
	public List<Column> getColumns() { return this.columns; }

	/** TODO: Documentation */
	public List<Index> getIndices() { return this.indices; }

	/** TODO: Documentation */
	public Query toQuery() {
		final StringBuilder b = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(this.name).append("` (");

		for (final Column column : this.getColumns()) {
			b.append(column.toQuery());
			b.append(", ");
		}

		for (final Index index : this.getIndices()) {
			b.append(index.toQuery());
			b.append(", ");
		}

		// Remove the last comma
		b.delete(b.length() - 2, b.length());
		b.append(')');

		if (this.engine != null) { b.append(" ENGINE=").append(this.engine.toString()); }
		if (this.charset != null) { b.append(" CHARSET=").append(this.charset.toString()); }
		if (this.comment != null) { b.append(" COMMENT='").append(this.comment.toString()).append("'"); }

		b.append(";");

		return Query.of(b.toString());
	}

	// --- Builder ---

	/** TODO: Documentation */
	public static Table.Builder builder(final String name) { return new Table.Builder().name(name); }

	/** TODO: Documentation */
	public static Table.Builder builder(final Table tableToCopyFrom) {
		final Table.Builder builder = Table.builder(tableToCopyFrom.getName());
		builder.comment = tableToCopyFrom.comment;
		builder.engine = tableToCopyFrom.engine;
		builder.charSet = tableToCopyFrom.charset;
		tableToCopyFrom.indices.forEach(i -> builder.indices.put(i.getName(), i));
		tableToCopyFrom.columns.forEach(c -> builder.columns.add(c));
		return builder;
	}

	public static class Builder implements de.craftolution.craftolibrary.Builder<Table> {

		private String name;
		private String comment;
		private Engine engine;
		private CharSet charSet;
		private final Map<String, Index> indices = new HashMap<>();
		private final List<Column> columns = new ArrayList<>();

		Builder() { }

		/** TODO: Documentation */
		public Builder name(final String tableName) { this.name = tableName; return this; }

		/** TODO: Documentation */
		public Builder comment(final String comment) { this.comment = comment; return this; }

		/** TODO: Documentation */
		public Builder engine(final Engine engine) { this.engine = engine; return this; }

		/** TODO: Documentation */
		public Builder charSet(final CharSet charSet) { this.charSet = charSet; return this; }

		/** TODO: Documentation */
		public Builder index(final String indexName, final IndexType type, final String... columns) { this.indices.put(indexName, new Index(indexName, type, columns)); return this; }

		// --- Columns methods ---

		private <T extends Column> Builder accept(final T column, @Nullable final Consumer<T> modifier) {
			if (modifier != null) { modifier.accept(column); }
			this.columns.add(column);
			return this;
		}

		/** TODO: Documentation */
		public Builder add(final Column column) { this.columns.add(column); return this; }

		/** TODO: Documentation */
		public Builder addBoolean(final String columnName, @Nullable final Consumer<BooleanColumn> modifier) { return this.accept(new BooleanColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addDecimal(final String columnName, final Consumer<DecimalColumn> modifier) { return this.accept(new DecimalColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addDouble(final String columnName, final Consumer<DoubleColumn> modifier) { return this.accept(new DoubleColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addFloat(final String columnName, final Consumer<FloatColumn> modifier) { return this.accept(new FloatColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addEnum(final String columnName, final Consumer<EnumColumn> modifier) { return this.accept(new EnumColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addInt(final String columnName, final Consumer<IntColumn> modifier) { return this.accept(new IntColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addString(final String columnName, final Consumer<VarCharColumn> modifier) { return this.accept(new VarCharColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addChar(final String columnName, final Consumer<CharColumn> modifier) { return this.accept(new CharColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addText(final String columnName, final Consumer<TextColumn> modifier) { return this.accept(new TextColumn().name(columnName), modifier); }

		/** TODO: Documentation */
		public Builder addTimestamp(final String columnName, final Consumer<TimestampColumn> modifier) { return this.accept(new TimestampColumn().name(columnName), modifier); }

		// --- Special column methods ---

		/** TODO: Documentation */
		public Builder addPrimaryColumn(final int length) { return this.accept(new IntColumn().name("id").index(IndexType.PRIMARY).autoIncrement().unsigned().length(length), null); }

		/** TODO: Documentation */
		public Builder addCreatedAt() { return this.accept(new TimestampColumn().name("created_at").standardCurrentTimestamp(), null); }

		@Override
		public Table build() throws IllegalArgumentException, IllegalStateException {
			for (final Column column : this.columns) {
				if (StringUtils.isReallyEmpty(column.getName())) {
					throw new IllegalStateException("The column " + column + " has an empty name!");
				}
			}

			for (final Index index : this.indices.values()) {
				if (StringUtils.isReallyEmpty(index.getName())) {
					throw new IllegalStateException("The index " + index + " has an empty name!");
				}
			}

			return new Table(this.name, this.columns, Lists.newArrayList(this.indices.values()), this.comment, this.engine, this.charSet);
		}

	}

}
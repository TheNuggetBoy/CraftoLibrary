package de.craftolution.craftolibrary.database.table;

import de.craftolution.craftolibrary.StringUtils;
import de.craftolution.craftolibrary.database.query.Query;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 10.02.2016
 */
public class Index {

	private final String name;
	private final IndexType type;
	private final String[] columns;

	Index(final String name, final IndexType type, final String[] columns) {
		this.name = name;
		this.type = type;
		this.columns = columns;
	}

	/** TODO: Documentation */
	public String getName() { return this.name; }

	/** TODO: Documentation */
	public IndexType getType() { return this.type; }

	/** TODO: Documentation */
	public String[] getColumns() { return this.columns; }

	/** TODO: Documentation */
	public Query toQuery() {
		final StringBuilder b = new StringBuilder();

		switch (this.type) {
			case FULLTEXT: b.append("FULLTEXT KEY `").append(this.name).append("` (").append(StringUtils.join(c -> "`" + c + "`",  ", ", this.columns)).append(")"); break;
			case INDEX: b.append("KEY `").append(this.name).append("` (").append(StringUtils.join(c -> "`" + c + "`",  ", ", this.columns)).append(")"); break;
			case PRIMARY: b.append("PRIMARY KEY KEY `").append(this.name).append("` (").append(StringUtils.join(c -> "`" + c + "`",  ", ", this.columns)).append(")"); break;
			case UNIQUE: b.append("UNIQUE KEY `").append(this.name).append("` (").append(StringUtils.join(c -> "`" + c + "`",  ", ", this.columns)).append(")"); break;
		}

		return Query.of(b.toString());
	}

}
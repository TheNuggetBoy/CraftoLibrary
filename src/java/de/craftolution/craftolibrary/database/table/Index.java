/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

import java.util.Arrays;

import de.craftolution.craftolibrary.StringUtils;
import de.craftolution.craftolibrary.database.query.Query;

/**
 * TODO: Documentation
 *
 * @author Fear837
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(columns);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Index other = (Index) obj;
		if (!Arrays.equals(columns, other.columns)) return false;
		if (name == null) {
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		if (type != other.type) return false;
		return true;
	}

}
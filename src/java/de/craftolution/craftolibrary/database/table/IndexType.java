/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.02.2016
 */
public enum IndexType {

	/** TODO: Documentation */
	PRIMARY ("PRIMARY KEY"),

	/** TODO: Documentation */
	UNIQUE ("UNIQUE KEY"),

	/** TODO: Documentation */
	INDEX ("INDEX"),

	/** TODO: Documentation */
	FULLTEXT ("FULLTEXT");

	private final String sql;

	IndexType(final String sql) { this.sql = sql; }

	@Override public String toString() { return this.sql; }

}
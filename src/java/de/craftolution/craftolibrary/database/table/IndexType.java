/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

public enum IndexType {

	PRIMARY ("PRIMARY KEY"),

	UNIQUE ("UNIQUE KEY"),

	INDEX ("INDEX"),

	FULLTEXT ("FULLTEXT");

	private final String sql;

	IndexType(final String sql) { this.sql = sql; }

	@Override public String toString() { return this.sql; }

}
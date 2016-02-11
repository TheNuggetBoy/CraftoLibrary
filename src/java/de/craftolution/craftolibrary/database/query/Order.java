/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

public enum Order {

	ASCENDING ("ASC"),

	DESCENDING ("DESC");

	private final String sql;

	private Order(final String sql) { this.sql = sql; }

	@Override
	public String toString() { return this.sql; }

}
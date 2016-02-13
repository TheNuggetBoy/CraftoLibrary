/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.query;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.02.2016
 */
public enum Order {

	/** TODO: Documentation */
	ASCENDING ("ASC"),

	/** TODO: Documentation */
	DESCENDING ("DESC");

	private final String sql;

	private Order(final String sql) { this.sql = sql; }

	@Override
	public String toString() { return this.sql; }

}
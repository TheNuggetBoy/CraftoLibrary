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
 * @since 13.02.2016
 */
public class RawQuery implements Query {

	private final String query;

	RawQuery(final String query) { this.query = query; }

	@Override
	public String toString() { return this.query; }

	@Override
	public RawQuery clone() {
		return new RawQuery(this.query);
	}

}
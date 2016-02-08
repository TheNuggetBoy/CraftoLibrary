package de.craftolution.craftolibrary.database.query;

public class RawQuery implements Query {

	private final String query;

	RawQuery(final String query) { this.query = query; }

	@Override
	public String toString() { return this.query; }
}
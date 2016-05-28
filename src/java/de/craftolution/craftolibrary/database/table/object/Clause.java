package de.craftolution.craftolibrary.database.table.object;

import de.craftolution.craftolibrary.database.Database;
import de.craftolution.craftolibrary.database.table.ObjectTable;

public class Clause {

	public static void main(String[] args) {
		// Example:
		Database db = null;
		ObjectTable<?> table = null;
		table.select(db, equal("id", 10).and( greatherThan("money", 100).or(lesserThan("money", 50) ).and(limit(10))));
	}
	
	public static Clause literal(String query) { return null; }
	
	public static Clause equal(String column, Object value) { return null; }
	
	public static Clause limit(int limit) { return null; }
	
	public static Clause greatherThan(String column, Object value) { return null; }
	
	public static Clause greatherOrEqual(String column, Object value) { return null; }
	
	public static Clause lesserThan(String column, Object value) { return null; }
	
	public static Clause lesserOrEqual(String column, Object value) { return null; }
	
	public static Clause unequal(String column, Object value) { return null; }

	public Clause and(Clause... clauses) { return this; }

	public Clause or(Clause... clauses) { return this; }

}
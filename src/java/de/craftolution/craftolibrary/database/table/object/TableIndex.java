package de.craftolution.craftolibrary.database.table.object;

import de.craftolution.craftolibrary.database.table.IndexType;

public @interface TableIndex {

	String name() default "";
	
	IndexType type();
	
	String[] columns() default {};
	
}
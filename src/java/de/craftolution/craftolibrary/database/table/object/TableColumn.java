package de.craftolution.craftolibrary.database.table.object;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import de.craftolution.craftolibrary.database.table.DataType;

@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {

	String name() default "";
	
	String comment() default "";
	
	boolean nullable() default false;
	
	String meta() default "";
	
	DataType type() default DataType.UNKNOWN;
	
	int length() default Integer.MIN_VALUE;
	
	boolean unsigned() default false;
	
	boolean autoIncrement() default false;
	
	String standard() default "";
	
	String[] values() default {};
	
	String onUpdate() default "";

	int precision() default Integer.MIN_VALUE;

}
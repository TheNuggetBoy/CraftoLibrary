package de.craftolution.craftolibrary.database.table.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.craftolution.craftolibrary.database.table.CharSet;
import de.craftolution.craftolibrary.database.table.Engine;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableMeta {

	String name();
	
	Engine engine();
	
	CharSet charSet();
	
	String comment();
	
}
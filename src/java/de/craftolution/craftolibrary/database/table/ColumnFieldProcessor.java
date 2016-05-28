package de.craftolution.craftolibrary.database.table;

import java.lang.reflect.Field;
import java.util.List;

@FunctionalInterface
public interface ColumnFieldProcessor {

	List<Column> resolve(Field field);

}
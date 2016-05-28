package de.craftolution.craftolibrary.database.table.object;

import java.lang.reflect.Field;
import java.util.Optional;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.table.Column;
import de.craftolution.craftolibrary.database.table.DataType;

public class AnnotatedColumn implements Column {

	private final String name;
	private final String comment;
	private final DataType type;
	private final boolean nullable;
	private final int length;
	private final String standard;
	private final boolean unsigned;
	private final int precision;
	
	public AnnotatedColumn(TableColumn annotation, Field field) {
		this.name = Check.notNullNotEmpty(annotation.name(), "The name of the annotated column on the field '" + field.getName() + "' is missing.");
		this.comment = annotation.comment();
		this.type = annotation.type();
		this.nullable = annotation.nullable();
		this.length = annotation.length();
		this.standard = annotation.standard();
		this.unsigned = annotation.unsigned();
		this.precision = annotation.precision();
	}
	
	@Override
	public String getName() { return this.name; }

	@Override
	public Optional<String> getComment() { return Optional.ofNullable(this.comment); }

	@Override
	public boolean isNullable() { return this.nullable; }

	@Override
	public Query toQuery() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

}
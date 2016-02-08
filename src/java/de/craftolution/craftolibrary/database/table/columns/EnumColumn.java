package de.craftolution.craftolibrary.database.table.columns;

public class EnumColumn extends AbstractColumn<String> {

	public EnumColumn values(final String... enumValues) {

		return this;
	}

	public EnumColumn values(final Enum<?> enumeration) {

		return this;
	}

}
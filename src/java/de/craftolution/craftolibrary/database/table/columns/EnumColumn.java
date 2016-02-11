package de.craftolution.craftolibrary.database.table.columns;

public class EnumColumn extends AbstractColumn<String, EnumColumn> {

	@Override
	protected EnumColumn instance() { return this; }

	public EnumColumn values(final String... enumValues) {

		return this;
	}

	public EnumColumn values(final Enum<?> enumeration) {

		return this;
	}

}
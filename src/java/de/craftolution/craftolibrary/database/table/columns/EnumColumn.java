/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class EnumColumn extends AbstractColumn<String, EnumColumn> {

	private String[] values;

	@Override
	protected EnumColumn instance() { return this; }

	/** TODO: Documentation */
	public EnumColumn values(final String... enumValues) {
		this.values = enumValues;
		return this;
	}

	/** TODO: Documentation */
	public EnumColumn values(final Class<?> enumeration) {
		if (enumeration.getEnumConstants() != null) {
			final String[] values = new String[enumeration.getEnumConstants().length];
			for (int i = 0; i < values.length; i++) {
				values[i] = enumeration.getEnumConstants()[i].toString();
			}
			this.values = values;
		}
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.values(this.values);
	}

}
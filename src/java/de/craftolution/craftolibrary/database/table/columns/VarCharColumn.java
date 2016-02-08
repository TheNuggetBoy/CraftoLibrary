package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.Lengthable;

public class VarCharColumn extends AbstractColumn<CharSequence> implements Lengthable<VarCharColumn> {

	private int length;

	@Override
	public VarCharColumn length(final int length) { this.length = length; return this; }

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.length);
	}

}
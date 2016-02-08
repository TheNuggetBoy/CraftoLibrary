package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;

public class FloatColumn extends AbstractColumn<Float> implements DoubleLenghtable<FloatColumn> {

	private int firstLength, secondLength;

	@Override
	public FloatColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.firstLength, this.secondLength);
	}

}
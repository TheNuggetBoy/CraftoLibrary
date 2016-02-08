package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;

public class DecimalColumn extends AbstractColumn<Double> implements DoubleLenghtable<DecimalColumn> {

	private int firstLength, secondLength;

	@Override
	public DecimalColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.firstLength, this.secondLength);
	}
}
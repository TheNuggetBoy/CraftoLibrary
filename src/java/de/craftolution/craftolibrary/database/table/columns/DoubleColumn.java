package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;

public class DoubleColumn extends AbstractColumn<Double, DoubleColumn> implements DoubleLenghtable<DoubleColumn> {

	private int firstLength, secondLength;

	@Override
	protected DoubleColumn instance() { return this; }

	@Override
	public DoubleColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.firstLength, this.secondLength);
	}

}
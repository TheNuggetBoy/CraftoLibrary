package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.AutoIncrementable;
import de.craftolution.craftolibrary.database.table.attributes.Lengthable;
import de.craftolution.craftolibrary.database.table.attributes.Unsignable;

public class IntColumn extends AbstractColumn<Integer, IntColumn> implements Unsignable<IntColumn>, Lengthable<IntColumn>, AutoIncrementable<IntColumn> {

	private int length;
	private boolean unsigned;
	private boolean autoIncrement;

	@Override
	protected IntColumn instance() { return this; }

	@Override
	public IntColumn length(final int length) { this.length = length; return this; }

	@Override
	public IntColumn unsigned() { this.unsigned = true; return this; }

	@Override
	public IntColumn autoIncrement() { this.autoIncrement = true; return this; }

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.length).unsigned(this.unsigned).autoIncrement(this.autoIncrement);
	}

}
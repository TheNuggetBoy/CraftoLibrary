/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;
import de.craftolution.craftolibrary.database.table.attributes.Unsignable;

public class DecimalColumn extends AbstractColumn<Double, DecimalColumn> implements DoubleLenghtable<DecimalColumn>, Unsignable<DecimalColumn> {

	private int firstLength, secondLength;
	private boolean unsigned;

	@Override
	protected DecimalColumn instance() { return this; }

	@Override
	public DecimalColumn unsigned() { this.unsigned = true; return this; }

	@Override
	public DecimalColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.unsigned(unsigned).length(this.firstLength, this.secondLength);
	}

}
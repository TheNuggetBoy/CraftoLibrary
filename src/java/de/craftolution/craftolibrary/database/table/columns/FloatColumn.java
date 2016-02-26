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

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class FloatColumn extends AbstractColumn<Float, FloatColumn> implements DoubleLenghtable<FloatColumn>, Unsignable<FloatColumn> {

	private int firstLength, secondLength;
	private boolean unsigned;

	@Override
	protected FloatColumn instance() { return this; }

	@Override
	public FloatColumn unsigned() { this.unsigned = true; return this; }

	@Override
	public FloatColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.unsigned(this.unsigned).length(this.firstLength, this.secondLength);
	}

}
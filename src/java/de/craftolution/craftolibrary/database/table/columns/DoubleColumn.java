/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

import de.craftolution.craftolibrary.database.table.attributes.DoubleLenghtable;
import de.craftolution.craftolibrary.database.table.attributes.Unsignable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class DoubleColumn extends AbstractColumn<Double, DoubleColumn> implements Serializable, DoubleLenghtable<DoubleColumn>, Unsignable<DoubleColumn> {

	private static final long serialVersionUID = -5058360214696423143L;

	private int firstLength, secondLength;
	private boolean unsigned;

	@Override
	protected DoubleColumn instance() { return this; }

	@Override
	public DoubleColumn unsigned() { this.unsigned = true; return this; }

	@Override
	public DoubleColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.unsigned(this.unsigned).length(this.firstLength, this.secondLength);
	}

}
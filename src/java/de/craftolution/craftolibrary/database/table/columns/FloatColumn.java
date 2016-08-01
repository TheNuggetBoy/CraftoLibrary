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
public class FloatColumn extends AbstractColumn<Float, FloatColumn> implements Serializable, DoubleLenghtable<FloatColumn>, Unsignable<FloatColumn> {

	private static final long serialVersionUID = -7176848484311225895L;

	private int firstLength, secondLength;
	private boolean lengthSet;
	private boolean unsigned;

	@Override
	protected FloatColumn instance() { return this; }

	@Override
	public FloatColumn unsigned() { this.unsigned = true; return this; }

	@Override
	public FloatColumn length(final int firstLength, final int secondLength) {
		this.firstLength = firstLength;
		this.secondLength = secondLength;
		this.lengthSet = true;
		return this;
	}

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		if (lengthSet) { builder.length(this.firstLength, this.secondLength); }
		return builder.unsigned(this.unsigned);
	}

}
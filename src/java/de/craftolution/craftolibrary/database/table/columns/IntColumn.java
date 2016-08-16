/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

import de.craftolution.craftolibrary.database.table.DataType;
import de.craftolution.craftolibrary.database.table.attributes.AutoIncrementable;
import de.craftolution.craftolibrary.database.table.attributes.Lengthable;
import de.craftolution.craftolibrary.database.table.attributes.Unsignable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class IntColumn extends AbstractColumn<Integer, IntColumn> implements Serializable, Unsignable<IntColumn>, Lengthable<IntColumn>, AutoIncrementable<IntColumn> {

	private static final long serialVersionUID = -7129709002071992844L;

	private Integer length;
	private Boolean unsigned;
	private Boolean autoIncrement;

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
		if (this.length != null) {
			if (this.length <= 3) { builder.type(DataType.TINYINT); }
			else if (this.length <= 5) { builder.type(DataType.SMALLINT); }
			else if (this.length <= 7) { builder.type(DataType.MEDIUMINT); }
			else if (this.length <= 11) { builder.type(DataType.INT); }
			else { builder.type(DataType.BIGINT); }
		} else { builder.type(DataType.INT); }
		if (unsigned != null) { builder.unsigned(this.unsigned); }
		if (autoIncrement != null) { builder.autoIncrement(autoIncrement); }
		return builder;
	}

}
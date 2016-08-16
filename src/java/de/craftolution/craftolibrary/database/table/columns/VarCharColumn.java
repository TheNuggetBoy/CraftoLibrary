/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

import de.craftolution.craftolibrary.database.table.attributes.Lengthable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class VarCharColumn extends AbstractColumn<CharSequence, VarCharColumn> implements Serializable, Lengthable<VarCharColumn> {

	private static final long serialVersionUID = 645353242551077057L;

	private Integer length;

	@Override
	protected VarCharColumn instance() { return this; }

	@Override
	public VarCharColumn length(final int length) { this.length = length; return this; }

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		if (length != null) { builder.length(this.length); }
		return builder;
	}

}
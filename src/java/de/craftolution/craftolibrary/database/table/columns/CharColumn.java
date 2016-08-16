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

public class CharColumn extends AbstractColumn<CharSequence, CharColumn> implements Lengthable<CharColumn>, Serializable {

	private static final long serialVersionUID = 5673983052359940949L;

	private Integer length;

	@Override
	protected CharColumn instance() { return this; }

	@Override
	public CharColumn length(final int length) { this.length = length; return this; }

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		if (length != null) { builder.length(this.length); }
		return builder;
	}

}
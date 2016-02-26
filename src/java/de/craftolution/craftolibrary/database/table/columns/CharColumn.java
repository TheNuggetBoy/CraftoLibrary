/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import de.craftolution.craftolibrary.database.table.attributes.Lengthable;

public class CharColumn extends AbstractColumn<Character, CharColumn> implements Lengthable<CharColumn> {

	private int length;

	@Override
	protected CharColumn instance() { return this; }

	@Override
	public CharColumn length(int length) { this.length = length; return this; }

	@Override
	protected ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.length(this.length);
	}

}
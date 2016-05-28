/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class TextColumn extends AbstractColumn<CharSequence, TextColumn> implements Serializable {

	private static final long serialVersionUID = 3184158312761509208L;

	@Override
	protected TextColumn instance() { return this; }

}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.io.Serializable;

public class BooleanColumn extends AbstractColumn<Boolean, BooleanColumn> implements Serializable {

	private static final long serialVersionUID = 4448853828333462568L;

	@Override
	protected BooleanColumn instance() { return this; }

}
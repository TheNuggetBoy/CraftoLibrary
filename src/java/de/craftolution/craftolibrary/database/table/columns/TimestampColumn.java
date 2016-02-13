/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table.columns;

import java.sql.Timestamp;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.02.2016
 */
public class TimestampColumn extends AbstractColumn<String, TimestampColumn> {

	@Override
	protected TimestampColumn instance() { return this; }

	/** TODO: Documentation */
	public TimestampColumn standardCurrentTimestamp() {
		super.standard("CURRENT_TIMESTAMP");
		return this;
	}

	/** TODO: Documentation */
	public TimestampColumn standard(final Timestamp timestamp) {
		super.standard(timestamp.toString());
		return this;
	}

}
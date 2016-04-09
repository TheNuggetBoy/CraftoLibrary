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

	private String onUpdate;

	@Override
	protected TimestampColumn instance() { return this; }

	/** TODO: Documentation */
	public TimestampColumn onUpdate(final String onUpdate) {
		this.onUpdate = onUpdate;
		return this;
	}

	/** TODO: Documentation */
	public TimestampColumn onUpdateCurrentTimestamp() {
		this.onUpdate = "CURRENT_TIMESTAMP";
		return this;
	}

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

	/** TODO: Documentation */
	@Override
	public ColumnDefinitionBuilder define(final ColumnDefinitionBuilder builder) {
		return builder.onUpdate(this.onUpdate);
	}

}
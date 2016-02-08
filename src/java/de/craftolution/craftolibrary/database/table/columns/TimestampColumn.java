package de.craftolution.craftolibrary.database.table.columns;

import java.sql.Timestamp;

public class TimestampColumn extends AbstractColumn<String> {

	public TimestampColumn standardCurrentTimestamp() {
		super.standard("CURRENT_TIMESTAMP");
		return this;
	}

	public TimestampColumn standard(final Timestamp timestamp) {
		super.standard(timestamp.toString());
		return this;
	}

}
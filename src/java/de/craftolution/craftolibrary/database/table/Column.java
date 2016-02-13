/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

import java.util.Optional;

import de.craftolution.craftolibrary.database.query.Query;

/**
 * Represents a column.
 *
 * @author Fear837
 * @since 12.02.2016
 */
public interface Column {

	/** @return Returns the name of this column. */
	String getName();

	/** @return Returns the commet, if present, that describes this comment.*/
	Optional<String> getComment();

	/** @return Returns whether or not this column allows null values. */
	boolean isNullable();

	/** Converts this column into a {@link Query}. */
	Query toQuery() throws IllegalStateException;

}
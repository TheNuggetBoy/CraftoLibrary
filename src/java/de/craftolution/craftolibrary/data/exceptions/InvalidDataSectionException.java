package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.DataSection;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class InvalidDataSectionException extends DataException {

	private static final long serialVersionUID = -1026737062922429124L;

	private final DataSection section;

	/** TODO: Documentation */
	public InvalidDataSectionException(final DataSection section, final String message) {
		super(message);
		this.section = section;
	}

	/** TODO: Documentation */
	public DataSection getSection() { return this.section; }

}
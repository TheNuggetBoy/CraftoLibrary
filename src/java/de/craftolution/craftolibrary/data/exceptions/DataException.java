package de.craftolution.craftolibrary.data.exceptions;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 18.04.2016
 */
public abstract class DataException extends Exception {

	private static final long serialVersionUID = -6521693938097458135L;

	public DataException(final String message, final Throwable cause) { super(message, cause); }

	public DataException(final String message) { super(message); }

}
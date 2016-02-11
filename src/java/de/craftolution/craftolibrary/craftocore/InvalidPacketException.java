package de.craftolution.craftolibrary.craftocore;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.01.2016
 */
public class InvalidPacketException extends RuntimeException {

	private static final long serialVersionUID = 5704813079216008197L;

	InvalidPacketException(final String message) {
		super(message);
	}

	InvalidPacketException(final Throwable cause) {
		super(cause);
	}

	InvalidPacketException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
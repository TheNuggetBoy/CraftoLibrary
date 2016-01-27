package de.craftolution.craftolibrary.craftocore;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.01.2016
 */
public class InvalidPacketException extends RuntimeException {

	private static final long serialVersionUID = 5704813079216008197L;

	InvalidPacketException(String message) {
		super(message);
	}

	InvalidPacketException(Throwable cause) {
		super(cause);
	}
	
	InvalidPacketException(String message, Throwable cause) {
		super(message, cause);
	}

}
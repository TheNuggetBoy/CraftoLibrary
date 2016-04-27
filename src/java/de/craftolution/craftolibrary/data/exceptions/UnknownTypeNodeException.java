package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.Node;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class UnknownTypeNodeException extends InvalidNodeException {

	private static final long serialVersionUID = -6489299070352575405L;
	
	private final Class<?> type;

	/** TODO: Documentation */
	public UnknownTypeNodeException(final Node node, Class<?> type, final String message) {
		super(node, message);
		this.type = type;
	}

	/** TODO: Documentation */
	public Class<?> getType() { return this.type; }

}
package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.Node;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 18.04.2016
 */
public abstract class InvalidNodeException extends DataException {

	private static final long serialVersionUID = -3343199201339341105L;

	private final Node node;

	/** TODO: Documentation */
	public InvalidNodeException(final Node node, final String message) {
		super(message);
		this.node = node;
	}

	/** TODO: Documentation */
	public Node getNode() { return this.node; }

}
package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.Node;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class EmptyNodeException extends InvalidNodeException {

	private static final long serialVersionUID = 1518069043053787965L;

	/** TODO: Documentation */
	public EmptyNodeException(final Node node, final String message) {
		super(node, message);
	}

	/** TODO: Documentation */
	public EmptyNodeException(final Node node) {
		this(node, "The node '" + node.getPath() + "' seems to be empty!");
	}

}
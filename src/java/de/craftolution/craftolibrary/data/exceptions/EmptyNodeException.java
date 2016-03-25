package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.DataSection;
import de.craftolution.craftolibrary.data.Node;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class EmptyNodeException extends InvalidDataSectionException {

	private static final long serialVersionUID = 1518069043053787965L;

	private final Node node;

	/** TODO: Documentation */
	public EmptyNodeException(final DataSection section, final Node node, final String message) {
		super(section, message);
		this.node = node;
	}

	/** TODO: Documentation */
	public EmptyNodeException(final DataSection section, final Node node) {
		this(section, node, "The node '" + node.getPath() + "' in the section '" + section.getId() + "' is to be empty!");
	}

	/** TODO: Documentation */
	public Node getNode() { return this.node; }

}
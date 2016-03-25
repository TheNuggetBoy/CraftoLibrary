package de.craftolution.craftolibrary.data.exceptions;

import de.craftolution.craftolibrary.data.DataPath;
import de.craftolution.craftolibrary.data.DataSection;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class MissingNodeException extends InvalidDataSectionException {

	private static final long serialVersionUID = 1518069043053787965L;

	private final DataPath missingNode;

	/** TODO: Documentation */
	public MissingNodeException(final DataSection section, final DataPath missingNode, final String message) {
		super(section, message);
		this.missingNode = missingNode;
	}

	/** TODO: Documentation */
	public MissingNodeException(final DataSection section, final String missingNode, final String message) {
		this(section, DataPath.of(missingNode), message);
	}

	/** TODO: Documentation */
	public MissingNodeException(final DataSection section, final DataPath missingNode) {
		this(section, missingNode, "The node '" + missingNode.toString() + "' seems to be missing in the datasection '" + section.getId() + "'!");
	}

	/** TODO: Documentation */
	public MissingNodeException(final DataSection section, final String missingNode) {
		this(section, DataPath.of(missingNode), "The node '" + section.getId() + "." + missingNode + "' seems to be missing in the datasection '" + section.getId() + "'!");
	}

	/** TODO: Documentation */
	public DataPath getMissingNode() { return this.missingNode; }

}
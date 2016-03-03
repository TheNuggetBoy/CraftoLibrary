package de.craftolution.craftolibrary.data;

import java.util.List;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public interface Node {

	/** TODO: Documentation */
	DataPath getPath();

	/** TODO: Documentation */
	String getId();

	/** TODO: Documentation */
	Node getParent();

	/** TODO: Documentation */
	Node setInt(int value); 

	Node getChild(DataPath path);

	List<Node> getChildren();

	/** TODO: Documentation */
	static Node of(DataPath path) { return null; }

}
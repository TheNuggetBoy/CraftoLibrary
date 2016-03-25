package de.craftolution.craftolibrary.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import de.craftolution.craftolibrary.ImmutableTuple;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataSection implements Serializable {

	private static final long serialVersionUID = -4351651518497546995L;

	private final Node rootNode;
	private int version;

	DataSection(final String sectionId) {
		this.rootNode = new Node(DataPath.of(sectionId), null, Lists.newArrayList(), null);
	}

	/** TODO: Documentation */
	public int getVersion() { return this.version; }

	/** TODO: Documentation */
	public String getId() { return this.rootNode.getId(); }

	/** TODO: Documentation */
	public DataSection withVersion(final int version) { this.version = version; return this; }

	/** TODO: Documentation */
	public Node getRootNode() { return this.rootNode; }

	/** TODO: Documentation */
	public Optional<Node> getNode(final String... path) { return this.getNode(DataPath.of(path)); }

	/** TODO: Documentation */
	public Optional<Node> getNode(final DataPath path) {
		final ImmutableTuple<String> elements = path.getPath();
		List<Node> possibleNodes = this.rootNode.getChildren();
		Node currentNode = null;

		outer: for (int i = 0; i < elements.length(); i++) {
			final String element = elements.at(i);
			boolean foundNode = false;

			inner: for (final Node node : possibleNodes) {
				if (node.getId().equals(element)) {
					possibleNodes = node.getChildren();
					currentNode = node;
					foundNode = true;
					System.out.println("Found subnode: " + currentNode.getPath());
					break inner;
				}
			}

			if (!foundNode) {
				break outer;
			}
		}

		System.out.println(currentNode != null ? "Found node: " + currentNode.getPath().toString() : "Failed to get node: " + path);
		return Optional.ofNullable(currentNode);
	}

	/** TODO: Documentation */
	public Node getOrCreateNode(final String... path) { return this.getOrCreateNode(DataPath.of(path)); }

	/** TODO: Documentation */
	public Node getOrCreateNode(final DataPath path) {
		System.out.println("Getting or creating node: " + path.toString());
		final ImmutableTuple<String> elements = path.getPath();
		List<Node> possibleNodes = this.rootNode.getChildren();
		Node currentNode = this.rootNode;

		for (int i = 0; i < elements.length(); i++) {
			final String element = elements.at(i);
			boolean foundNode = false;

			inner: for (final Node node : possibleNodes) {
				if (node.getId().equals(element)) {
					possibleNodes = node.getChildren();
					currentNode = node;
					foundNode = true;
					break inner;
				}
			}

			if (!foundNode) {
				final Node newNode = new Node(DataPath.of(elements.slice(0, i + 1)), currentNode, new ArrayList<>(1), null);

				if (currentNode != null) { currentNode.getChildren().add(newNode); } // When newNode is a rootNode, currentNode is most likely null
				else { this.rootNode.getChildren().add(newNode); }

				currentNode = newNode;

				possibleNodes = newNode.getChildren();
				System.out.println("Created subnode: " + newNode.getPath().toString());
			}
		}

		System.out.println(currentNode != null ? "Found node: " + currentNode.getPath().toString() : "Failed to get or create node: " + path);
		return currentNode;
	}

}
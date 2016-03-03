package de.craftolution.craftolibrary.data;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataSection {

	Node rootNode = Node.of(DataPath.of(""));

	Optional<Node> getNode(DataPath path) {
		String[] tree = path.getPath();
		Node currentNode = rootNode;
		for (String element : tree) {
			for (Node node : currentNode.getChildren()) {
				if (node.getId().equals(element)) {
					currentNode = node;
				}
			}
		}
		
		if (currentNode.getPath().equals(path)) {
			return Optional.of(currentNode);
		}
		else { return Optional.empty(); }
	}

	Node getOrCreateNode(DataPath path) {
		@Nullable Node foundNode = this.getNode(path).orElse(null);
		if (foundNode == null) {
			String[] tree = path.getPath();
			Node currentNode = rootNode;
			for (String element : tree) {
				boolean found = false;
				
				for (Node node : currentNode.getChildren()) {
					if (node.getId().equals(element)) {
						found = true;
						currentNode = node;
					}
				}
				
				if (!found) {
					String[] newPath = new String[tree.length + 1];
					System.arraycopy(tree, 0, newPath, 0, tree.length);
					newPath[tree.length] = element;
					Node newNode = Node.of(DataPath.of(newPath));
					currentNode.getChildren().add(newNode);
					currentNode = newNode;
				}
			}

			//foundNode = Node.of(path); 
			//this.nodes.put(path.toString(), node);
		}

		return null;
	}
	
}
package de.craftolution.craftolibrary.data;

import javax.annotation.Nullable;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataPath {

	private final String[] path;

	@Nullable private DataPath root;
	@Nullable private String pathAsString;

	private DataPath(String[] path) {
		this.path = path;
	}

	String[] getPath() { return this.path; }

	public DataPath getRoot() {
		if (this.root == null) {
			this.root = DataPath.of(this.path[0]);
		}
		return this.root;
	}

	@Override
	public String toString() {
		if (this.pathAsString == null) {
			final StringBuilder b = new StringBuilder();
			for (String node : this.path) {
				b.append(node).append('.');
			}
			b.delete(b.length() - 1, b.length());
			this.pathAsString = b.toString();
		}

		return this.pathAsString;
	}

	/** TODO: Documentation */
	public static DataPath of(DataPath parent, String... nodes) {
		final String[] combinedPath = new String[parent.path.length + nodes.length];
		System.arraycopy(parent.path, 0, combinedPath, 0, parent.path.length);
		System.arraycopy(nodes, 0, combinedPath, parent.path.length, nodes.length);
		return new DataPath(combinedPath);
	}

	/** TODO: Documentation */
	public static DataPath of(String... nodes) {
		return new DataPath(nodes);
	}

}
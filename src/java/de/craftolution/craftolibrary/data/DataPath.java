package de.craftolution.craftolibrary.data;

import java.io.Serializable;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.ImmutableTuple;
import de.craftolution.craftolibrary.Tuple;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataPath implements Serializable {

	private static final long serialVersionUID = -3805367674627638422L;

	private final Tuple<String> path;

	@Nullable private transient DataPath root;
	@Nullable private transient DataPath parent;
	@Nullable private transient String pathAsString;

	private DataPath(final Tuple<String> path) {
		this.path = path;
	}

	/** TODO: Documentation */
	ImmutableTuple<String> getPath() { return this.path; }

	/** TODO: Documentation */
	public String getId() { return this.path.at(this.path.length() - 1); }

	/** TODO: Documentation */
	public DataPath getParent() {
		if (this.parent == null) {
			this.parent = DataPath.of(this.path.slice(0, this.path.length() - 1));
		}

		return this.parent;
	}

	/** TODO: Documentation */
	public DataPath getRoot() {
		if (this.root == null) {
			this.root = DataPath.of(this.path.at(0));
		}
		return this.root;
	}

	@Override
	public String toString() {
		if (this.pathAsString == null) {
			if (this.path.length() < 1) {
				this.pathAsString = "";
			}
			else if (this.path.length() == 1) {
				return this.path.at(0);
			}
			else {
				final StringBuilder b = new StringBuilder();
				for (final String node : this.path) {
					b.append(node).append('.');
				}
				b.delete(b.length() - 1, b.length());
				this.pathAsString = b.toString();
			}
		}

		return this.pathAsString;
	}

	/** TODO: Documentation */
	public static DataPath of(final DataPath parent, final String... nodes) {
		return new DataPath(Tuple.concatenate(parent.path, nodes));
	}

	/** TODO: Documentation */
	public static DataPath of(final Tuple<String> nodes) {
		return new DataPath(nodes);
	}

	/** TODO: Documentation */
	public static DataPath of(final String... nodes) {
		return new DataPath(Tuple.of(nodes));
	}

}
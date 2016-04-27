package de.craftolution.craftolibrary.data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.data.exceptions.EmptyNodeException;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class Node implements Serializable {

	private static final long serialVersionUID = -1529094506917187610L;

	private final DataPath path;
	private final Node parent;
	private final List<Node> children;

	@Nullable protected Object value;

	Node(final DataPath path, final Node parent, final List<Node> children, final Object value) {
		this.path = path;
		this.parent = parent;
		this.children = children;
		this.value = value;
	}

	/** TODO: Documentation */
	public DataPath getPath() { return this.path; }

	/** TODO: Documentation */
	public String getId() { return this.path.getId(); }

	/** TODO: Documentation */
	public Node getParent() { return this.parent != null ? this.parent : this; }

	/** TODO: Documentation */
	public Optional<Node> getChild(final String id) {
		for (final Node node : this.children) {
			if (node.getId().equals(id)) {
				return Optional.of(node);
			}
		}
		return Optional.empty();
	}

	/** TODO: Documentation */
	public Node getOrCreateChild(final String id) {
		for (final Node node : this.getChildren()) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		final Node node = new Node(DataPath.of(this.getPath(), id), this.getParent(), this.getChildren(), null);
		this.getChildren().add(node);
		return node;
	}

	/** TODO: Documentation */
	public List<Node> getChildren() { return this.children; }

	@SuppressWarnings("unchecked")
	private <T> T cast(final Class<T> type) { return this.value == null ? null : (T) this.value; }

	@Override
	public String toString() { return "'" + this.getPath() + "'='" + this.value + "'"; }

	// --- Getters ---

	/** TODO: Documentation */
	public Optional<Object> get() { return Optional.ofNullable(this.value); }

	/** TODO: Documentation */
	public <T> Optional<T> get(final Class<? extends T> type) throws ClassCastException {
		if (this.value == null) { return Optional.empty(); }
		if (this.value.getClass().isAssignableFrom(type)) { return Optional.ofNullable( this.cast(type) ); }
		else { throw new ClassCastException("Cannot cast " + type.getClass().getName() + " to " + type.getName() + " in node: " + this.getPath() + "." + this.getId() + "!"); }
	}

	/** TODO: Documentation */
	public <T> T getOrThrow(final Class<? extends T> type) throws EmptyNodeException {
		if (this.get().isPresent()) { throw new EmptyNodeException(this); }
		return this.get(type).get();
	}

	/** TODO: Documentation */
	public <T> T get(final Class<? extends T> type, final T defaultValue) throws ClassCastException {
		final Optional<T> result = this.get(type);
		return result.orElse(defaultValue);
	}

	// --- Setters ---

	/** TODO: Documentation */
	public Node set(final Object value) { this.value = value; return this; }

	/** TODO: Documentation */
	public Node setInt(final int value) { this.value = value; return this; }

}
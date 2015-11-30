package de.craftolution.craftolibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.11.2015
 */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

	private final Map<K, V> map = new HashMap<K, V>();

	private MapBuilder() { }

	/** TODO: Documentation */
	@Override
	public Map<K, V> build() {
		return this.map;
	}

	/** TODO: Documentation */
	public MapBuilder<K, V> put(final K key, final V value) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		this.map.put(key, value);
		return this;
	}

	/** TODO: Documentation */
	public static <K, V> MapBuilder<K, V> create(final K key, final V value) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		return new MapBuilder<K, V>();
	}

}
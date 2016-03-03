package de.craftolution.craftolibrary.data;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class MutableDataContainer extends DataSection {

	MutableDataContainer set(DataPath path, int value) {
		super.getOrCreateNode(path).setInt(value);
		return this;
	}

}
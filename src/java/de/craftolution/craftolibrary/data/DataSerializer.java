package de.craftolution.craftolibrary.data;

import de.craftolution.craftolibrary.data.exceptions.SerializationException;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public interface DataSerializer {

	/** TODO: Documentation */
	void serialize(DataStorage storage) throws SerializationException;

}

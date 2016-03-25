package de.craftolution.craftolibrary.data;

import java.util.function.Function;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.03.2016
 */
public class RegisteredData {

	private final Class<? extends Data> clazz;
	private final Function<DataSection, DeserializedData> deserializer;

	RegisteredData(final Class<? extends Data> clazz, final Function<DataSection, DeserializedData> deserializer) {
		this.clazz = clazz;
		this.deserializer = deserializer;
	}

	/** TODO: Documentation */
	public Class<? extends Data> getDataClass() { return this.clazz; }

	/** TODO: Documentation */
	public Function<DataSection, DeserializedData> getDeserializer() { return this.deserializer; }

}
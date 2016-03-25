package de.craftolution.craftolibrary.data;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

import de.craftolution.craftolibrary.Result;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.03.2016
 */
public interface DataDeserializer {

	/** TODO: Documentation */
	Optional<RegisteredData> getData(Class<? extends Data> dataClass);

	/** TODO: Documentation */
	<T extends Data> boolean registerData(Class<T> dataClass, Function<DataSection, DeserializedData> deserializer);

	/** TODO: Documentation */
	Result<DataStorage> deserialize(Path source);

}
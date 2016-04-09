package de.craftolution.craftolibrary.data;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.Maps;

import de.craftolution.craftolibrary.Result;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.03.2016
 */
public class ObjectDataDeserializer implements DataDeserializer {

	final Map<String, RegisteredData> dataMap = Maps.newHashMap();

	@Override
	public Optional<RegisteredData> getData(final Class<? extends Data> dataClass) { return Optional.ofNullable(this.dataMap.get(dataClass.getName())); }

	@Override
	public <T extends Data> boolean registerData(final Class<T> dataClass, final Function<DataSection, DeserializationResult> deserializer) {
		if (!this.getData(dataClass).isPresent()) {
			final RegisteredData data = new RegisteredData(dataClass, deserializer);
			this.dataMap.put(dataClass.getName(), data);
			return true;
		}
		return false;
	}

	@Override
	public Result<DataStorage> deserialize(final Path source) {
		try {
			final ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(source));
			final DataSectionSet sections = (DataSectionSet) stream.readObject();
			final DataStorage storage = new DataStorage();

			for (int i = 0; i < sections.size(); i++) {
				final DataSection section = sections.getSection(i);
				final String className = sections.getSectionClass(i);
				System.out.println("Got datasection with className: " + className);

				final RegisteredData registeredData = this.dataMap.get(className);
				if (registeredData != null) {
					try {
						final DeserializationResult result = registeredData.getDeserializer().apply(section);
						if (result.getData().isPresent()) {
							storage.offer(result.getData().get());
							System.out.println("Offering data " + result.getClass().getName().toString() + " to storage!");
						}
						else {
							System.err.println("Failed to handle deserialized data!");
							if (result.getException().isPresent()) {
								throw result.getException().get();
							}
						}
					}
					catch (final Exception e) {
						System.out.println("Something went wrong while trying to derserialize the data '" + registeredData.getDataClass().getName() + "' in the following datasection: " + section.getId());
						e.printStackTrace();
					}
				}
				else { System.err.println("Missing deserializer for a specific data."); } // TODO: Return a DataDeserializationResult or something?
			}

			return Result.of(storage);
		}
		catch (final Exception e) { return Result.ofException(e); }
	}

}

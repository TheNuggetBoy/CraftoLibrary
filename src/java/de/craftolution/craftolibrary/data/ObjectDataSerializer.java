package de.craftolution.craftolibrary.data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.collect.MutableClassToInstanceMap;

import de.craftolution.craftolibrary.data.exceptions.SerializationException;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 12.03.2016
 */
public class ObjectDataSerializer implements DataSerializer {

	private final Path target;

	/** TODO: Documentation */
	public ObjectDataSerializer(final Path target) {
		this.target = target;
	}

	@Override
	public void serialize(final DataStorage storage) throws SerializationException {
		final MutableClassToInstanceMap<Data> dataMap = storage.dataMap;
		final DataSectionSet sections = new DataSectionSet();

		for (final Data data : dataMap.values()) {
			final DataSection section = new DataSection(data.getClass().getName());
			if (data.serialize(section)) {
				sections.insertSection(data.getClass(), section);
			}
		}

		try {
			final OutputStream stream = Files.newOutputStream(this.target);
			final ObjectOutputStream objectStream = new ObjectOutputStream(stream);
			objectStream.writeObject(sections);
			objectStream.close();
		}
		catch (final IOException e) { throw new SerializationException("Failed to serialize data into " + this.target.toString() + ".", e); }
	}

}
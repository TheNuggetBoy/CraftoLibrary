package de.craftolution.craftolibrary.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import de.craftolution.craftolibrary.Result;
import de.craftolution.craftolibrary.data.exceptions.EmptyNodeException;
import de.craftolution.craftolibrary.data.exceptions.InvalidDataSectionException;
import de.craftolution.craftolibrary.data.exceptions.MissingNodeException;
import de.craftolution.craftolibrary.data.result.DataTransactionResult;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 04.03.2016
 */
public class Tests {

	static class TestData implements Data {

		int hp;
		int maxhp;

		@Override
		public boolean serialize(final DataSection container) {
			container.withVersion(1)
			.getOrCreateNode("hp")
			.setInt(this.hp)
			.getParent()
			.getOrCreateChild("maxhp")
			.setInt(this.maxhp);

			return true;
		}

		@Override
		public String toString() { return this.hp + "hp/" + this.maxhp + "maxhp"; }

		public static void register(final DataDeserializer deserializer) {
			deserializer.registerData(TestData.class, section -> {
				try {
					if (section.getVersion() < 1) {
						throw new InvalidDataSectionException(section, "The section version " + section.getVersion() + " is outdated.");
					}

					final TestData data = new TestData();

					final Node hp = section.getNode("hp").orElseThrow(() -> new MissingNodeException(section, "hp"));
					data.hp = hp.get(Integer.class).orElseThrow(() -> new EmptyNodeException(section, hp));

					final Node maxHP = section.getNode("maxhp").orElseThrow(() -> new MissingNodeException(section, "maxhp"));
					data.maxhp = maxHP.get(Integer.class).orElseThrow(() -> new EmptyNodeException(section, hp));

					return new DeserializationResult(data);
				}
				catch (final InvalidDataSectionException e) { return new DeserializationResult(e); }
			});
		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final Path FILE_PATH = Paths.get("mytestfile.txt");

	public static void main(final String[] args) throws Exception {
		// Create DataStorage
		DataStorage storage = new DataStorage();

		// Create some data and offer it to the storage
		final TestData data = new TestData();
		data.hp = 77;
		data.maxhp = 200;

		final DataTransactionResult transactionResult = storage.offer(data);
		if (transactionResult.isSuccess()) {
			transactionResult.rollback();
			storage.offer(data);
		}

		// Create a serializer and serialize storage
		Files.createFile(Tests.FILE_PATH);
		final DataSerializer serializer = new ObjectDataSerializer(Tests.FILE_PATH);
		serializer.serialize(storage);

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Create deserializer
		final DataDeserializer deserializer = new ObjectDataDeserializer();
		TestData.register(deserializer); // We have to register our testdata so the deserializer knows what to do when it comes across such data.

		// Deserialize the file into a DataStorage
		final Result<DataStorage> result = deserializer.deserialize(Tests.FILE_PATH);

		if (result.isPresent()) {
			storage = result.get();

			// Get the data
			final Optional<TestData> optionalData = storage.get(TestData.class);

			if (optionalData.isPresent()) {
				System.out.println("Found data: " + optionalData.get().toString());
			}
			else { System.out.println("Data is not present in datastorage!"); }
		}
		else {
			// Something went wrong while deserializing, so check if an exception occured and print to console.
			if (result.getException().isPresent()) {
				System.out.println("Failed to deserialize data because: ");
				throw result.getException().get();
			}
			else {
				System.out.println("Failed to deserialize data for an unknown reason.");
			}
		}

	}
}
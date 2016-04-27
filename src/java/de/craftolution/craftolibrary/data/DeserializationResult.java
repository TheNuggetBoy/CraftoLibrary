package de.craftolution.craftolibrary.data;

import java.util.Optional;

import de.craftolution.craftolibrary.data.exceptions.DataException;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.03.2016
 */
public class DeserializationResult {

	private final Data data;
	private final DataException exception;

	DeserializationResult(final Data data) {
		this.data = data;
		this.exception = null;
	}

	DeserializationResult(final DataException exception) {
		this.data = null;
		this.exception = exception;
	}

	/** TODO: Documentation */
	public Optional<Data> getData() { return Optional.ofNullable(this.data); }

	/** TODO: Documentation */
	public Optional<DataException> getException() { return Optional.ofNullable(this.exception); }

}
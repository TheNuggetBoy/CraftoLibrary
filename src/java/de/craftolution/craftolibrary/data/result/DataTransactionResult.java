package de.craftolution.craftolibrary.data.result;

import java.util.Optional;

import de.craftolution.craftolibrary.data.Data;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataTransactionResult {

	public enum ResultType {

		SUCCESS,

		FAILURE;

	}

	Optional<FailureReason> getFailureReason() { return null; }

	Optional<Data> getReplacedData() { return null; }

}
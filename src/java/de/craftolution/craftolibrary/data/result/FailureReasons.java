package de.craftolution.craftolibrary.data.result;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class FailureReasons {

	private FailureReasons() { }

	/** TODO: Documentation */
	public static final FailureReason UNKNOWN = () -> "An unknown error occured while trying to transfer the data to the dataholder.";

	/** TODO: Documentation */
	public static final FailureReason CANT_ROLLBACK_FAILED_TRANSACTION = () -> "Failed transactions cannot be rolled back.";

}
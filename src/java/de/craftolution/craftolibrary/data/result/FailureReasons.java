package de.craftolution.craftolibrary.data.result;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class FailureReasons {

	private FailureReasons() { }

	public static final FailureReason UNKNOWN = () -> "An unknown error occured while trying to transfer the data to the dataholder.";

}
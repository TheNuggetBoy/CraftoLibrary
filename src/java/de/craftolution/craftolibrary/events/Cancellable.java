package de.craftolution.craftolibrary.events;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
public interface Cancellable {

	void cancel();

	void uncancel();

	boolean isCancelled();

}
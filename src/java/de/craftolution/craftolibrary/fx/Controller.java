package de.craftolution.craftolibrary.fx;

import javafx.application.Application;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.01.2016
 */
public interface Controller {

	/** Called when the application has loaded this controller. */
	default void onInit(Application application) { }

	default void onShown() { }

	default void onHidden() { }

}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
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
	default void onInit(final Application application) { }

	default void onShown() { }

	default void onHidden() { }

}

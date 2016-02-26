/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.events;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
public interface Cancellable {

	/** TODO: Documentation */
	void cancel();

	/** TODO: Documentation */
	void uncancel();

	/** TODO: Documentation */
	boolean isCancelled();

}
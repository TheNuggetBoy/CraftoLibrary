/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.logging;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 15.09.2015
 */
@FunctionalInterface
public interface LogFormatter {

	/** TODO: Documentation */
	String format(LogRecord record);

}
package de.craftolution.craftolibrary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks the method as a {@code blocking} method.
 * {@code Blocking} methods usually access data from a file or
 * a database and therefore take some time for processing & accessing the
 * certain data, so the caller has to keep that in mind.
 *
 * @author Fear837
 * @since 26.03.2016
 */
@Target(ElementType.METHOD)
public @interface Blocking { }
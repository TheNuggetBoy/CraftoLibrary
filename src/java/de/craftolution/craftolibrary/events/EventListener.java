package de.craftolution.craftolibrary.events;

import java.util.function.Consumer;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
@FunctionalInterface
public interface EventListener<T extends Event> extends Consumer<T> {

	@Override
	void accept(T event);

}
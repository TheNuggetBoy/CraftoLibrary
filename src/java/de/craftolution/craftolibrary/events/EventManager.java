/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.craftolution.craftolibrary.SortedList;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
public class EventManager {

	private final HashMap<Class<?>, List<EventListener<Event>>> listenerMap = new HashMap<>();

	/** TODO: Documentation */
	public void post(final Event event) {
		final List<EventListener<Event>> listeners = this.listenerMap.get(event.getClass());
		if (listeners != null && !listeners.isEmpty()) {
			for (final EventListener<Event> listener : listeners) {
				listener.accept(event);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> void register(final Class<T> event, final EventListener<T> listener) {
		this.checkEventClass(event);
		final List<EventListener<Event>> listeners = this.listenerMap.get(event);
		if (!listeners.contains(listener)) {
			listeners.add((EventListener<Event>) listener); // Unsafe cast
		}

		final Class<?> superClass = event.getSuperclass();
		while (superClass != null && (superClass.equals(Event.class) || Event.class.isAssignableFrom(superClass))) {
			final Class<? extends Event> eventSuperClass = (Class<? extends Event>) event.getSuperclass();

			this.checkEventClass(eventSuperClass);
			this.listenerMap.get(superClass).add((EventListener<Event>) listener); // Unsafe cast
		}
	}

	@SuppressWarnings("unchecked")
	public List<EventListener<? extends Event>> register(final Object listenerContainer) throws SecurityException {
		final List<EventListener<? extends Event>> listeners = new ArrayList<>();

		for (final Method method : listenerContainer.getClass().getMethods()) {
			try {
				if (!method.isAnnotationPresent(EventHandler.class)) { continue; }
				if (method.getParameterTypes().length != 1) { continue; }
				if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) { continue; }

				final EventListener<Event> listener = (event) -> {
					try { method.invoke(listenerContainer, event); }
					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { e.printStackTrace(); }
				};

				this.registerUnsafe((Class<Event>) method.getParameterTypes()[0], listener);

				listeners.add(listener);
			}
			catch (final Exception e) { e.printStackTrace(); }
		}

		return listeners;
	}

	private void registerUnsafe(final Class<Event> event, final EventListener<Event> listener) {
		this.checkEventClass(event);
		final List<EventListener<Event>> listeners = this.listenerMap.get(event);
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}

		final Class<?> superClass = event.getSuperclass();
		final Class<?> eventSuperClass = event.getSuperclass();

		this.checkEventClass(eventSuperClass);
		this.listenerMap.get(superClass).add(listener);
	}

	private void checkEventClass(final Class<?> clazz) {
		if (!this.listenerMap.containsKey(clazz)) {
			this.listenerMap.put(clazz, new SortedList<EventListener<Event>>());
		}
	}

}
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
public class EventManager {

	private final HashMap<Class<?>, Set<EventListener<Event>>> listenerMap = new HashMap<>();

	/** TODO: Documentation */
	public void clearListeners() { this.listenerMap.clear(); }

	/** TODO: Documentation */
	public void post(final Event event) {
		Class<?> clazz = event.getClass();
		while (clazz != null && (clazz.equals(Event.class) || Event.class.isAssignableFrom(clazz))) {
			Set<EventListener<Event>> listeners = this.listenerMap.get(clazz);
			if (listeners != null && !listeners.isEmpty()) {
				for (final EventListener<Event> listener : listeners) {
					listener.accept(event);
				}
			}
			
			if (clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
				for (int i = 0; i < clazz.getInterfaces().length; i++) {
					Class<?> interfaceClass = clazz.getInterfaces()[i];
					
					if (interfaceClass.equals(Event.class) || Event.class.isAssignableFrom(interfaceClass)) {
						listeners = this.listenerMap.get(interfaceClass);
						if (listeners != null && !listeners.isEmpty()) {
							for (final EventListener<Event> listener : listeners) {
								listener.accept(event);
							}
						}
					}
				}
			}
			
			clazz = clazz.getSuperclass();
		}		
	}

	/** TODO: Documentation */
	public <T extends Event> void register(final Class<T> event, final EventListener<T> listener) {
		this.checkEventClass(event);
		final Set<EventListener<Event>> listeners = this.listenerMap.get(event);
		if (!listeners.contains(listener)) {
			@SuppressWarnings("unchecked")
			EventListener<Event> castedListener = (EventListener<Event>)listener;
			listeners.add(castedListener); // Unsafe cast
		}
//		// Not needed since the post() method already iterates over the given event superclasses.
//		Class<?> currentClass = event;
//		while (currentClass != null && (currentClass.equals(Event.class) || Event.class.isAssignableFrom(currentClass))) {			
//			Class<? extends Event> castedClass = (Class<? extends Event>) currentClass;
//			
//			this.checkEventClass(castedClass);
//			this.listenerMap.get(castedClass).add((EventListener<Event>) listener); // Unsafe cast
//			
//			if (currentClass.getInterfaces() != null && currentClass.getInterfaces().length > 0) {
//				for (int i = 0; i < currentClass.getInterfaces().length; i++) {
//					Class<?> interfaceClass = currentClass.getInterfaces()[i];
//					
//					if (interfaceClass.equals(Event.class) || Event.class.isAssignableFrom(interfaceClass)) {
//						this.checkEventClass(interfaceClass);
//						this.listenerMap.get(interfaceClass).add((EventListener<Event>) listener); // Unsafe cast?
//					}
//				}
//			}
//			
//			currentClass = currentClass.getSuperclass();
//		}
		
//		final Class<?> superClass = event.getSuperclass();
//		while (superClass != null && (superClass.equals(Event.class) || Event.class.isAssignableFrom(superClass))) {
//			final Class<? extends Event> eventSuperClass = (Class<? extends Event>) event.getSuperclass();
//
//			this.checkEventClass(eventSuperClass);
//			this.listenerMap.get(superClass).add((EventListener<Event>) listener); // Unsafe cast
//		}
	}

	/** TODO: Documentation */
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

				@SuppressWarnings("unchecked")
				Class<Event> castedClass = (Class<Event>) method.getParameterTypes()[0];
				this.registerUnsafe(castedClass, listener);

				listeners.add(listener);
			}
			catch (final Exception e) { e.printStackTrace(); }
		}

		return listeners;
	}

	private void registerUnsafe(final Class<Event> event, final EventListener<Event> listener) {
//		// Not needed since the post() method already iterates over the given event superclasses.
//		Class<?> currentClass = event;
//		while (currentClass != null && (currentClass.equals(Event.class) || Event.class.isAssignableFrom(currentClass))) {			
//			@SuppressWarnings("unchecked")
//			Class<? extends Event> castedClass = (Class<? extends Event>) currentClass;
//			
//			this.checkEventClass(castedClass);
//			this.listenerMap.get(castedClass).add(listener);
//			
//			if (currentClass.getInterfaces() != null && currentClass.getInterfaces().length > 0) {
//				for (int i = 0; i < currentClass.getInterfaces().length; i++) {
//					Class<?> interfaceClass = currentClass.getInterfaces()[i];
//					
//					if (interfaceClass.equals(Event.class) || Event.class.isAssignableFrom(interfaceClass)) {
//						this.checkEventClass(interfaceClass);
//						this.listenerMap.get(interfaceClass).add((EventListener<Event>) listener); // Unsafe cast?
//					}
//				}
//			}
//			
//			currentClass = currentClass.getSuperclass();
//		}

		this.checkEventClass(event);
		final Set<EventListener<Event>> listeners = this.listenerMap.get(event);
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	private void checkEventClass(final Class<?> clazz) {
		System.out.println("Checking for class: " + clazz);
		
		if (!this.listenerMap.containsKey(clazz)) {
			this.listenerMap.put(clazz, new LinkedHashSet<EventListener<Event>>());
		}
	}

}
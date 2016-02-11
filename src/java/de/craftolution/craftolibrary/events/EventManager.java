package de.craftolution.craftolibrary.events;

//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import de.craftolution.craftolibrary.SortedList;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.12.2015
 */
public class EventManager {

	//	private final HashMap<Class<Event>, List<EventListener<Event>>> listenerMap = new HashMap<>();
	//
	//	public void post(final Event event) {
	//		final List<EventListener<Event>> listeners = this.listenerMap.get(event.getClass());
	//		if (listeners != null && !listeners.isEmpty()) {
	//			for (EventListener<Event> listener : listeners) {
	//				listener.accept(event); // ??
	//			}
	//		}
	//	}
	//
	//	public <T extends Event> void register(final Class<T> event, final EventListener<T> listener) {
	//		this.checkEventClass(event);
	//		final List<EventListener<Event>> listeners = this.listenerMap.get(event);
	//		if (!listeners.contains(listener)) {
	//			listeners.add(listener);
	//		}
	//
	//		Class<?> superClass = event.getSuperclass();
	//		while (superClass != null && (superClass.equals(Event.class) || Event.class.isAssignableFrom(superClass))) {
	//			@SuppressWarnings("unchecked")
	//			Class<? extends Event> eventSuperClass = (Class<? extends Event>) event.getSuperclass();
	//
	//			this.checkEventClass(eventSuperClass);
	//			this.listenerMap.get(superClass).add(listener);
	//		}
	//	}
	//
	////	@SuppressWarnings("unchecked")
	////	private void registerUnsafe(Class<Event> event, EventListener<Event> listener) {
	////		this.checkEventClass((Class<? extends Event>) event);
	////		final List<EventListener<? extends Event>> listeners = this.listenerMap.get(event);
	////		if (!listeners.contains(listener)) {
	////			listeners.add(listener);
	////		}
	////
	////		Class<?> superClass = event.getSuperclass();
	////			Class<? extends Event> eventSuperClass = (Class<? extends Event>) event.getSuperclass();
	////
	////			this.checkEventClass(eventSuperClass);
	////			this.listenerMap.get(superClass).add(listener);
	////		}
	////	}
	//
	//	@SuppressWarnings("unchecked")
	//	public List<EventListener<? extends Event>> register(final Object listenerContainer) throws SecurityException {
	//		final List<EventListener<? extends Event>> listeners = new ArrayList<>();
	//
	//		for (final Method method : listenerContainer.getClass().getMethods()) {
	//			try {
	//				if (!method.isAnnotationPresent(EventHandler.class)) { continue; }
	//				if (method.getParameterTypes().length != 1) { continue; }
	//				if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) { continue; }
	//
	//				EventListener<? extends Event> listener = (event) -> {
	//					try { method.invoke(listenerContainer, event); }
	//					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { e.printStackTrace(); }
	//				};
	//
	//				this.registerUnsafe((Class<? extends Event>) method.getParameterTypes()[0], listener);
	//
	//				listeners.add(listener);
	//			}
	//			catch (Exception e) { e.printStackTrace(); }
	//		}
	//
	//		return listeners;
	//	}
	//
	//	void checkEventClass(Class<? extends Event> clazz) {
	//		if (!this.listenerMap.containsKey(clazz)) {
	//			this.listenerMap.put(clazz, new SortedList<EventListener<?>>());
	//		}
	//	}

}
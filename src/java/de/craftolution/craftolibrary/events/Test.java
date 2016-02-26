package de.craftolution.craftolibrary.events;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 22.02.2016
 */
public class Test {

	static class MyEvent implements Event {

		public int a;

	}

	public static void main(final String[] args) {
		final EventManager manager = new EventManager();

		manager.register(MyEvent.class, event -> {
			System.out.println("Received: " + event.a);
		});

		MyListener listener = new MyListener();
		manager.register(listener);
		
		final MyEvent e = new MyEvent();
		e.a = 5;

		
		
		for (int i = 0; i < 10; i++) {
			e.a = i;
			manager.post(e);
		}

	}

	static class MyListener {

		@EventHandler
		public void onEvent(MyEvent event) {
			System.out.println("RECEIVED: " + event.a);
		}

	}

}
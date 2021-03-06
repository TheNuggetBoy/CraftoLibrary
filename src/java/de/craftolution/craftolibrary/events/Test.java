package de.craftolution.craftolibrary.events;

/**
 * TODO: Documentation
 *
 * @author Fear837
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

		System.out.println("----------------");
		
		final MyListener listener = new MyListener();
		manager.register(listener);

		final MyEvent e = new MyEvent();
		e.a = 5;



		for (int i = 0; i < 2; i++) {
			e.a = i;
			manager.post(e);
		}

	}

	static class MyListener {

		@EventHandler
		public void onEvent(final MyEvent event) {
			System.out.println("RECEIVED: " + event.a);
		}

		@EventHandler
		public void onOtherEvent(Event event) {
			System.out.println("LOL");
		}

	}

}
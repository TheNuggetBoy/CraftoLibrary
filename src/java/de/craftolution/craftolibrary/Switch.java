package de.craftolution.craftolibrary;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 04.03.2016
 */
public class Switch {

	private final Object instance;
	private boolean foundCase = false;
	private boolean suspended = false;

	Switch(Object instance) { this.instance = instance; }
	
	public static Switch of(Object instance) {
		return new Switch(instance);
	}

	public Switch onCase(Object... instances) {
		if (suspended) { return this; }

		for (Object obj : instances) {
			if (this.instance.equals(obj)) {
				foundCase = true;
			}
		}
		return this;
	}

	public Switch then(Runnable runnable) {
		if (foundCase) { runnable.run(); }
		return this;
	}

	public Switch suspend() {
		if (this.suspended) { return this; }
		if (this.foundCase) { this.suspended = true; }
		return this;
	}
	
	public Switch onDefault(Runnable handler) {
		if (this.suspended) { return this; }

		handler.run();
		return this;
	}

}
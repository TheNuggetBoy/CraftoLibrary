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

	Switch(final Object instance) { this.instance = instance; }

	public static Switch of(final Object instance) {
		return new Switch(instance);
	}

	public Switch onCase(final Object... instances) {
		if (this.suspended) { return this; }

		for (final Object obj : instances) {
			if (this.instance.equals(obj)) {
				this.foundCase = true;
			}
		}
		return this;
	}

	public Switch then(final Runnable runnable) {
		if (this.foundCase) { runnable.run(); }
		return this;
	}

	public Switch suspend() {
		if (this.suspended) { return this; }
		if (this.foundCase) { this.suspended = true; }
		return this;
	}

	public Switch onDefault(final Runnable handler) {
		if (this.suspended) { return this; }

		handler.run();
		return this;
	}

}
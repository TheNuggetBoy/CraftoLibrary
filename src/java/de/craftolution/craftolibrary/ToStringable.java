/*
 * Copyright (C) 2015 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

/**
 * TODO: File description for ToStringable.java
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 14.08.2015 23:32:00
 * @see <a href="https://github.com/Craftolution">Craftolution on Github</a>
 */
public interface ToStringable {

	@Override
	String toString();

	/** TODO: Documentation */
	default ToStringBuilder buildToString() {
		return new ToStringBuilder(this);
	}

	public class ToStringBuilder {

		private final StringBuilder builder;
		private boolean appended;

		ToStringBuilder(final Object instance) {
			this.builder = new StringBuilder(instance.getClass().getSimpleName()+"@"+Integer.toHexString(instance.hashCode()));
			this.appended = false;
		}

		/** TODO: Documentation */
		public ToStringBuilder with(final String key, Object value) {
			if (key != null) {
				if (value == null) { value = "null"; }
				if (!this.appended) { this.builder.append("{"); this.appended = true; }
				this.builder.append(key + ":'" + value + "', ");
			}
			return this;
		}

		@Override
		public String toString() {
			if (this.appended) {
				this.builder.delete(this.builder.length()-2, this.builder.length());
				this.builder.append("}");
			}
			return this.builder.toString();
		}
	}
}
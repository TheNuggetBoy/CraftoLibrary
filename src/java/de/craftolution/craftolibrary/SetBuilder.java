package de.craftolution.craftolibrary;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder<T> implements Builder<Set<T>> {

	private final Set<T> set = new HashSet<>();
	
	private SetBuilder() { }
	
	@Override
	public Set<T> build() { return this.set; }
	
	public SetBuilder<T> add(T element) {
		this.set.add(element);
		return this;
	}
	
	public SetBuilder<T> addAll(Collection<? extends T> elements) {
		this.set.addAll(elements);
		return this;
	}
	
	public SetBuilder<T> addAll(T[] elements) {
		for (T element : elements) {
			this.set.add(element);
		}
		return this;
	}

	public static <T> SetBuilder<T> create(T element) {
		return new SetBuilder<T>().add(element);
	}
}
package de.craftolution.craftolibrary;

import java.util.function.Consumer;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 02.10.2015
 */
public class Chain<E, T> extends Pair<E, T> {

	private Chain(final E value, final T next) {
		super(value, next);
	}

	/** TODO: Documentation */
	public E get() {
		return this.getFirst();
	}

	/** TODO: Documentation */
	public T next() {
		return this.getSecond();
	}

	public Chain<E, T> handle(final Consumer<E> consumer) {
		consumer.accept(this.get());
		return this;
	}

	/** TODO: Documentation */
	public static <E, T> Chain<E, T> of(final E value, final T next) {
		return new Chain<E, T>(value, next);
	}

	/** TODO: Documentation */
	public static void main(final String[] args) {
		Chain<String, Chain<Integer, Chain<Boolean, Chain<String, String>>>> test;
		test = Chain.of("Hello", Chain.of(5, Chain.of(true, Chain.of("Firstname", "Lastname"))));

		test.handle(string -> System.out.println(string))
		.next().handle(age -> System.out.println("Age: bla"))
		.next()
		.next();
	}

}
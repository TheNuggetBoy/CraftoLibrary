/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.google.common.annotations.Beta;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 01.10.2015
 */
@Beta
public class StringReplacer {

	private final Map<String, Supplier<String>> replacementMap;

	@Beta
	public StringReplacer(final Map<String, Supplier<String>> replacementMap) {
		this.replacementMap = replacementMap;
	}

	@Beta
	public String apply(final String input) {
		return this.apply(input.toCharArray());
	}

	@Beta
	public String apply(final char[] input) {
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
			final char c = input[i];
			if (c == '$') {
				//System.out.println("Found $! at index " + i + " (next char: " + input[i+1] + ")");
				inner: for (final Entry<String, Supplier<String>> entry : this.replacementMap.entrySet()) {
					final char[] key = entry.getKey().toCharArray();
					boolean matches = true;
					//System.out.println("Checking key: " + entry.getKey());

					matcher: for (int k = 0; k < key.length; k++) {
						if (key[k] != input[i + k + 1]) {
							//System.out.println(key[k] + " doesn't match with " + input[i + k + 1] + "("+i+", "+k+")");
							matches = false;
							break matcher;
						}
					}

					if (matches) {
						final String value = entry.getValue().get();
						b.append(value);
						i += key.length; // Also i++ after this one finished
						break inner;
					}
				}
			}
			else { b.append(c); }
		}
		return b.toString();
	}

	public static void main(final String[] args) {
		final int runs = 100000;
		System.out.println("Launching the StringReplacer.java Test with " + runs + "...");

		final String currentName = "Peter";
		final String currentAge = "17";

		// --- String replacer ---
		final Map<String, Supplier<String>> map = new HashMap<>();
		map.put("name", () -> currentName);
		map.put("age", () -> currentAge);

		final StringReplacer replacer = new StringReplacer(map);
		final long start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			replacer.apply("Hello World, im $name and im $age years old.");
		}
		final long end = System.nanoTime();

		// --- Original replacing ---
		final long start2 = System.nanoTime();

		final String firstToken = "$name";
		final String secondToken = "$age";

		for (int i = 0; i < 100000; i++) {
			final String sentence = "Hello World, im $name and im $age years old.";
			sentence.replace(firstToken, currentName).replace(secondToken, currentAge);
		}
		final long end2 = System.nanoTime();

		// --- Results ---
		final long duration = end - start;
		System.out.println("StringReplacer took " + (end - start) / 1000 / 1000 + " ms. That's " + duration / runs + " nanoseconds for every replace!");

		final long duration2 = end2 - start2;
		System.out.println("Standard string replacing took " + duration2 / 1000 / 1000 + " ms. That's " + duration2 / runs + " nanoseconds for every replace!");
	}
}
/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.annotations.Beta;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 01.10.2015
 */
@Beta
public class ContextualStringReplacer<Context> {

	private final Map<String, Function<Context, String>> replacementMap;

	@Beta
	public ContextualStringReplacer(final Map<String, Function<Context, String>> replacementMap) {
		this.replacementMap = replacementMap;
	}

	@Beta
	public String apply(final String input, final Context context) {
		return this.apply(input.toCharArray(), context);
	}

	@Beta
	public String apply(final char[] input, final Context context) {
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
			final char c = input[i];
			if (c == '$') {
				//System.out.println("Found $! at index " + i + " (next char: " + input[i+1] + ")");
				inner: for (final Entry<String, Function<Context, String>> entry : this.replacementMap.entrySet()) {
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
						final String value = entry.getValue().apply(context);
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

}
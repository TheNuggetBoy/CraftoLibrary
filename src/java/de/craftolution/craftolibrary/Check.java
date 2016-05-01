/*
 * Copyright (C) 2014 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nullable;

/**
 * Utility class to check strings or validate values.
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 25.10.2014
 * @see <a href="https://github.com/Craftolution">CraftolutionDE on Github</a>
 * @see Validate
 */
public class Check {

	private Check() { }

	/**
	 * Checks whether or not the given object is present.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is present and {@code false} if it is {@code null}.
	 */
	public static final boolean isPresent(final Object o) {
		return o != null;
	}

	/** TODO: Documentation */
	public static final <T> boolean ifPresent(final T object, final Consumer<T> consumer) {
		if (object != null) { consumer.accept(object); return true; }
		return false;
	}

	/** TODO: Documentation */
	public static final <T> T getPresent(final T firstObj, final T secondObj) { return firstObj != null ? firstObj : secondObj; }

	/**
	 * Checks whether or not the given object is {@code null}.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is {@code null} and {@code false} if not.
	 */
	public static final boolean isNull(final Object o) {
		return o == null;
	}

	/**
	 * Checks whether or not the given object is an instance of the given class.
	 * @param o - The object to check.
	 * @param clazz - The class to check.
	 * @return Returns {@code true} if the object is not {@code null} and is an instance of the given class.
	 */
	public static final boolean isInstanceOf(@Nullable final Object o, final Class<?> clazz) {
		if (o != null && clazz != null) {
			return clazz.isInstance(o);
		}
		return false;
	}

	/**
	 * Checks whether or not the given string is empty.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is empty or {@code null} and {@code false} if not.
	 */
	public static final boolean isEmpty(final String s) {
		return StringUtils.isEmpty(s);
	}

	/**
	 * Checks whether or not the given collection is empty.
	 * @param c - The collection to check.
	 * @return Returns {@code true} if the collection is empty or {@code null} and {@code false} if not.
	 */
	public static final boolean isEmpty(final Collection<?> c) {
		return c == null || c.isEmpty();
	}

	/**
	 * Checks whether or not the given array is empty.
	 * @param array - The array to check.
	 * @return Returns {@code true} if the array is empty, {@code null}, or all fields in the given array are {@code null}.
	 */
	public static final boolean isEmpty(final Object[] array) {
		if (array != null) {
			if (array.length > 0) {
				for (final Object object : array) {
					if (object != null) { return false; }
				}
				return true;
			}
			else { return true; }
		}
		else { return true; }
	}

	/**
	 * Checks whether or not the given stringbuilder is empty.
	 * @param sb - The stringbuilder to check.
	 * @return Returns {@code true} if the stringbuilder is empty or {@code null}.
	 */
	public static final boolean isEmpty(final StringBuilder sb) {
		return StringUtils.isEmpty(sb);
	}

	/**
	 * Checks whether or not the given object is a boolean.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a boolean and {@code false} if not.
	 */
	public static final boolean isBoolean(final Object o) {
		return o != null && (o instanceof Boolean || o.toString().matches("(true)|(false)|1|0"));
	}

	/**
	 * Checks whether or not the given object is binary.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is binary and {@code false} if not.
	 */
	public static final boolean isBinary(final Object o) {
		return o != null && o.toString().matches("(0b)?(0|1)+");
	}

	/**
	 * Checks whether or not the given object is a decimal.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is decimal and {@code false} if not.
	 */
	public static final boolean isDecimal(final Object o) {
		return o != null && o.toString().matches("()?[0-9]+");
	}

	/**
	 * Checks whether or not the given object is hexadecimal.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is hexadecimal and {@code false} if not.
	 */
	public static final boolean isHexadecimal(final Object o) {
		return o != null && o.toString().matches("-?(0x)?[0-9a-f]+");
	}

	/**
	 * Checks whether or not the given object is a byte.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a byte and {@code false} if not.
	 */
	public static final boolean isByte(final Object o) {
		if (o != null) {
			if (o instanceof Byte) { return true; }
			else if (o.toString().matches("-?(0|1)?[0-9]{0,2}")) {
				try {
					Short.parseShort(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the given object is a short.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a short and {@code false} if not.
	 */
	public static final boolean isShort(final Object o) {
		if (o != null) {
			if (o instanceof Short) { return true; }
			else if (o.toString().matches("-?(0|1|2)?[0-9]{0,2}")) {
				try {
					Short.parseShort(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the given object is an integer.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is an int and {@code false} if not.
	 */
	public static final boolean isInt(final Object o) {
		if (o != null) {
			if (o instanceof Integer) { return true; }
			else if (o.toString().matches("-?[0-9]+")) {
				try {
					Integer.parseInt(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/** TODO: Documentation */
	public static final Integer parseInt(final Object o, final int defaultValue) {
		if (o != null) {
			if (o instanceof Integer) {
				return (Integer) o;
			}
			else if (o.toString().matches("-?[0-9]+")) {
				try {
					final int i = Integer.parseInt(o.toString());
					return i;
				} catch (final Exception e) { }
			}
		}
		return defaultValue;
	}

	/** TODO: Documentation */
	public static final float parseFloat(final Object o, final float defaultValue) {
		if (o != null) {
			if (o instanceof Float) {
				return (Float) o;
			}
			else if (o.toString().matches("-?[0-9]*(\\.)?[0-9]*(f|F)?")) {
				try {
					final float i = Float.parseFloat(o.toString());
					return i;
				} catch (final Exception e) { }
			}
		}
		return defaultValue;
	}

	/** TODO: Documentation */
	public static final double parseDouble(final Object o, final double defaultValue) {
		if (o != null) {
			if (o instanceof Double) {
				return (Double) o;
			}
			else if (o.toString().matches("-?[0-9]*(\\.)?[0-9]*")) {
				try {
					final double i = Double.parseDouble(o.toString());
					return i;
				} catch (final Exception e) { }
			}
		}
		return defaultValue;
	}

	/** TODO: Documentation */
	public static final boolean parseBoolean(final Object o, final boolean defaultValue) {
		if (o != null) {
			if (o instanceof Boolean) {
				return (Boolean) o;
			}
			else if (o.toString().matches("(true)|(false)|1|0")) {
				try {
					final boolean i = Boolean.parseBoolean(o.toString());
					return i;
				} catch (final Exception e) { }
			}
		}
		return defaultValue;
	}

	/**
	 * Checks whether or not the given object is a long.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a long and {@code false} if not.
	 */
	public static final boolean isLong(final Object o) {
		if (o != null) {
			if (o instanceof Long) { return true; }
			else if (o.toString().matches("-?[0-9]+(l|L)?")) {
				try {
					Long.parseLong(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the given object is a float.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a float and {@code false} if not.
	 */
	public static final boolean isFloat(final Object o) {
		if (o != null) {
			if (o instanceof Float) { return true; }
			else if (o.toString().matches("-?[0-9]*(\\.)?[0-9]*(f|F)?")) {
				try {
					Float.parseFloat(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the given object is a double.
	 * @param o - The object to check.
	 * @return Returns {@code true} if the object is a double and {@code false} if not.
	 */
	public static final boolean isDouble(final Object o) {
		if (o != null) {
			if (o instanceof Double) { return true; }
			else if (o.toString().matches("-?[0-9]*(\\.)?[0-9]*")) {
				try {
					Double.parseDouble(o.toString());
					return true;
				} catch (final Exception e) { }
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the given string is a word.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a word and {@code false} if not.
	 */
	public static final boolean isWord(final String s) {
		return StringUtils.isWord(s);
	}

	/**
	 * Checks whether or not the given string is a command.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a command and {@code false} if not.
	 */
	public static final boolean isCommandWord(final String s) {
		return StringUtils.isCommandWord(s);
	}

	/**
	 * Checks whether or not the given string is a website.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a website and {@code false} if not.
	 */
	public static final boolean isWebsite(final String s) {
		return StringUtils.isWebsite(s);
	}

	/**
	 * Checks whether or not the given string could be a minecraft username.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string could be a minecraft username and {@code false} if not.
	 */
	public static final boolean isMinecraftname(final String s) {
		return StringUtils.isMinecraftname(s);
	}

	/**
	 * Checks whether or not the given string is a rank.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a rank and {@code false} if not.
	 */
	public static final boolean isRank(final String s) {
		return StringUtils.isRank(s);
	}

	/**
	 * Checks whether or not the given string is a unformated {@link UUID}.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a unformated uniqueid and {@code false} if not.
	 */
	public static final boolean isUnformatedUUID(final String s) {
		return StringUtils.isUnformatedUUID(s);
	}

	/**
	 * Checks whether or not the given string is a {@link UUID}.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a uniqueid and {@code false} if not.
	 */
	public static final boolean isUUID(final String s) {
		return StringUtils.isUUID(s);
	}

	// --- Argument checks ---

	/**
	 * Checks that the specified object is not {@code null}.
	 * Otherwise throws a {@link IllegalArgumentException} with the specified message.
	 *
	 * @param object - The object to check
	 * @param message - The exception message
	 * @return Returns the given {@code object}.
	 * @throws IllegalArgumentException If the specified object is {@code null}.
	 */
	public static <T> T notNull(final T object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNull(object, message)  was called with a absent message o_O"); }
		if (object == null) { throw new IllegalArgumentException(message); }
		return object;
	}

	/** TODO: Documentation */
	public static String notNullNotEmpty(final String object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNullNotEmpty(object, message)  was called with a absent message o_O"); }
		if (object == null || object.isEmpty() || object.replace(" ", "").isEmpty()) { throw new IllegalArgumentException(message); }
		return object;
	}

	/** TODO: Documentation */
	public static <T> Collection<T> notNullNotEmpty(final Collection<T> object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNullNotEmpty(object, message)  was called with a absent message o_O"); }
		if (object == null || object.isEmpty()) { throw new IllegalArgumentException(message); }
		return object;
	}

	/** TODO: Documentation */
	public static <T> List<T> notNullNotEmpty(final List<T> object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNullNotEmpty(object, message)  was called with a absent message o_O"); }
		if (object == null || object.isEmpty()) { throw new IllegalArgumentException(message); }
		return object;
	}

	/** TODO: Documentation */
	public static <T> Set<T> notNullNotEmpty(final Set<T> object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNullNotEmpty(object, message)  was called with a absent message o_O"); }
		if (object == null || object.isEmpty()) { throw new IllegalArgumentException(message); }
		return object;
	}

	/** TODO: Documentation */
	public static <T> T[] notNullNotEmpty(final T[] object, final String message) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNullNotEmpty(object, message)  was called with a absent message o_O"); }
		if (object == null || object.length < 1) { throw new IllegalArgumentException(message); }
		return object;
	}

	/**
	 * Checks that the specified objects are not {@code null}.
	 * You can split the identifiers of the given objects so the right identifier
	 * will be displayed in the final exception message.
	 *
	 * <p><b>Example 1:</b> The following will result in {@code "The length cannot be null!"}
	 * <pre> String input = "Hello";
	 * Integer length = null;
	 * Double count = 16.0;
	 * Check.notNull("The input/length/count cannot be null!", myInput, myLength, myCount);
	 * </pre> </p>
	 *
	 * <p><b>Example 2:</b> The following will result in {@code "The length and count cannot be null!"}
	 * <pre> String input = "Hello";
	 * Integer length = null;
	 * Double count = null;
	 * Check.notNull("The input/length/count cannot be null!", myInput, myLength, myCount);
	 * </pre> </p>
	 *
	 * @param message - The message to display when at least one of the objects is {@code null}.
	 * @param objects - The objects to check
	 * @throws IllegalArgumentException If one of the given objects is {@code null}.
	 */
	public static void nonNulls(final String message, final Object... objects) throws IllegalArgumentException {
		if (message == null) { throw new IllegalArgumentException("Check#notNull(string, object[]) was called with a absent message o_O"); }
		if (objects.length <= 0) { throw new IllegalArgumentException("Check#notNull(string, object[]) was called with an empty array!"); }
		if (objects.length == 1) { Check.notNull(objects[0], message); }

		// Fast check, dont need to process all that stuff down there if all objects are present anyway
		boolean allPresent = true;
		for (final Object obj : objects) {
			if (obj == null) { allPresent = false; }
		}
		if (allPresent) { return; }

		// Atleast one element is null
		String[] identifiers = null;
		String wordThatContainsTheIdentifiers = null;
		for (final String word : message.split(" ")) {
			if (word.contains("/")) {
				final String[] wordIdentifiers = word.split("/");
				if (wordIdentifiers.length >= objects.length) {
					identifiers = wordIdentifiers;
					wordThatContainsTheIdentifiers = word;
					break;
				}
			}
		}

		final StringBuilder nullIdentifiers = new StringBuilder();
		boolean foundNullObject = false;

		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null) {
				foundNullObject = true;
				if (identifiers != null && wordThatContainsTheIdentifiers != null && identifiers.length > i) {
					nullIdentifiers.append(identifiers[i]);
					nullIdentifiers.append(" and ");
				}
				else { throw new IllegalArgumentException(message); }
			}
		}

		if (foundNullObject) {
			nullIdentifiers.delete(nullIdentifiers.length() - 5, nullIdentifiers.length());
			throw new IllegalArgumentException(message.replace(wordThatContainsTheIdentifiers, nullIdentifiers.toString()));
		}
	}

	/**
	 * Checks that the specified condition is {@code true}.
	 * Otherwise throws a {@link IllegalArgumentException} with the specified message.
	 *
	 * @param expression - The expression to check
	 * @param message - The exception message
	 * @throws IllegalArgumentException If the specified condition isn't {@code true}.
	 */
	public static void isTrue(final boolean expression, final String message) throws IllegalArgumentException {
		if (!expression) { throw new IllegalArgumentException(message); }
	}

	/**
	 * Checks that the specified string is neither {@code null} nor empty.
	 * Otherwise throws a {@link IllegalArgumentException} with the specified message.
	 *
	 * @param string - The string to check
	 * @param message - The exception message
	 * @throws IllegalArgumentException If the specified string is {@code null} or empty.
	 * @return Returns the given {@code string}.
	 */
	public static String notEmpty(final String string, final String message) throws IllegalArgumentException {
		if (string == null || string.isEmpty() || string.replace(" ", "").isEmpty()) { throw new IllegalArgumentException(message); }
		return string;
	}

	/** TODO: Documentation */
	public static <T> T[] notEmpty(final T[] array, final String message) throws IllegalArgumentException {
		if (array == null || array.length <= 0) { throw new IllegalArgumentException(message); }
		return array;
	}

	/**
	 * Validate that the specified argument is an instance of the given class or {@code null};
	 * otherwise throwing an exception.
	 *
	 * @param object the object to check
	 * @param clazz the class to check
	 * @throws IllegalArgumentException if the object is either {@code null} or is not an instance of the given class.
	 */
	public static void instanceOf(final Object object, final Class<? extends Object> clazz) throws IllegalArgumentException {
		if (object != null && clazz != null && clazz.isInstance(object)) {
			return;
		}
		throw new IllegalArgumentException("The validated object is not the right instance");
	}

	/**
	 * Validate that the specified argument is an instance of the given class or {@code null};
	 * otherwise throwing an exception.
	 *
	 * <pre>Check.instanceOf(myObject, MyClass.class, "The object is invalid!");</pre>
	 *
	 * @param object the object to check
	 * @param clazz the class to check
	 * @param message the exception message if invalid
	 * @throws IllegalArgumentException if the object is either {@code null} or is not an instance of the given class.
	 */
	public static void instanceOf(final Object object, final Class<? extends Object> clazz, final String message) throws IllegalArgumentException {
		if (object != null && clazz != null && clazz.isInstance(object)) {
			return;
		}
		throw new IllegalArgumentException(message);
	}

}
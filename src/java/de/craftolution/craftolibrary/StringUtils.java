/*
 * Copyright (C) 2014 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.base.Strings;

import de.craftolution.craftolibrary.ToStringable.ToStringBuilder;

/**
 * TODO: File description.
 *
 * @author Fear837, Pingebam
 * @version 1.0
 * @since 26.10.2014
 * @see <a href="https://github.com/Craftolution">CraftolutionDE on Github</a>
 * @see Strings
 * @see String
 */
public class StringUtils {

	public static final String BULLET = String.valueOf('\u25cf'); //String.valueOf((char) 9679);
	public static final String BACKSLASH = "\\";
	public static final String MASKED_BACKSLASH = StringUtils.BACKSLASH + StringUtils.BACKSLASH;
	public static final String QUOTE = "\'";
	public static final String MASKED_QUOTE = StringUtils.BACKSLASH + StringUtils.QUOTE;
	public static final String DOUBLE_QUOTE = "\"";
	public static final String MASKED_DOUBLE_QUOTE = StringUtils.BACKSLASH + StringUtils.DOUBLE_QUOTE;
	public static final char[] LETTERS = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
			'r', 's', 't', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };

	private StringUtils() { }

	private static SecureRandom random = new SecureRandom();

	/** TODO: Documentation */
	public static ToStringBuilder buildToString(final Object instance) {
		return new ToStringBuilder(instance);
	}

	/** TODO: Documentation */
	public static boolean isReallyEmpty(final String string) {
		return string == null || string.replace(" ", "").isEmpty();
	}

	/** TODO: Documentation */
	public static boolean doesStringFollow(final char[] array, final int start, final String target) {
		int targetIndex = 0;
		final char[] targetArray = target.toCharArray();
		for (int i = start; i < start + target.length(); i++) {
			final char targetChar = targetArray[targetIndex];
			if (array[i] != targetChar) {
				return false;
			}
			targetIndex++;
		}
		return true;
	}

	/** TODO: Documentation */
	public static String generateRandomString(final int length) {
		Check.isTrue(length > 0, "The length must be greater than 0");

		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			b.append(StringUtils.LETTERS[StringUtils.random.nextInt(StringUtils.LETTERS.length)]);
		}

		return b.toString();
	}

	/** TODO: Documentation */
	public static String escapeJson(final String string) {
		return string.replace(StringUtils.BACKSLASH, StringUtils.MASKED_BACKSLASH).replace(StringUtils.QUOTE, StringUtils.MASKED_QUOTE).replace(StringUtils.DOUBLE_QUOTE, StringUtils.MASKED_DOUBLE_QUOTE);
	}

	/**
	 * Converts the given parameters into a map.
	 * @param params - The parameters array to convert.
	 * @return Returns a map containing all parameters found in the given array.
	 */
	public static Map<String, String> getParameters(final String... params){
		Check.notNull("The params cannot be null!", (Object[]) params);
		final Map<String, String> map = new HashMap<String, String>();
		for (final String parameter : params) {
			if (parameter != null && parameter.length() > 2 && parameter.contains(":")) {
				final String[] pair = parameter.split(":");
				if (pair.length == 2) {
					map.put(pair[0].toLowerCase(), pair[1]);
				}
			}
		}
		return map;
	}

	/**
	 * Checks if the specified string contains all the given sequences.
	 * @param string - The {@link String} to check.
	 * @param sequences - The sequences that have to be contained in the string.
	 * @return Returns {@code true}, if the given string contains all specified sequences.
	 */
	public static boolean contains(final String string, final CharSequence... sequences) {
		for (final CharSequence cs : sequences) {
			if (!string.contains(cs)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the specified string contains any of the given sequences.
	 * @param string - The {@link String} to check.
	 * @param sequences - The sequences that have to be contained in the string.
	 * @return Returns {@code true}, if atleast one of the char sequences was found in the given string.
	 */
	public static boolean containsAny(final String string, final CharSequence... sequences) {
		for (final CharSequence cs : sequences) {
			if (string.contains(cs)) {
				return true;
			}
		}
		return false;
	}

	public static void replaceForEach(final CharSequence target, final CharSequence replacement, final String... array) {
		for (int i=0; i<array.length; i++) {
			array[i] = array[i].replace(target, replacement);
		}
	}

	/**
	 * Checks if one of the given sequences equals the given string.
	 * @param string - The {@link String} to check.
	 * @param sequences - The sequences.
	 * @return Returns {@code true}, if atleast one sequence equals the given string.
	 */
	public static boolean equalsIgnoreCase(final String string, final CharSequence... sequences) {
		for (final CharSequence cs : sequences) {
			if (string.equalsIgnoreCase(cs.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p> Converts the given ascii decimal code into a string. For example:
	 * <pre> System.out.print( StringUtils.toString(65) ); </pre>
	 * Will produce the following output: {@code "A"}. </p>
	 *
	 * @param ascii - The ascii code to convert.
	 * @return Returns the character as a string that represents the given ascii code.
	 */
	public static String toString(final int ascii) {
		return Character.toString((char)ascii).intern();
	}

	/**
	 * Returns a string representation of the object or an empty string if the given object is {@code null}.
	 * @param object - The object
	 * @return Returns a string representation of the object or an empty string if the given object is {@code null}.
	 */
	public static String toString(@Nullable final Object object) {
		return String.valueOf(object);
	}

	/**
	 * Checks if the given string says true or yes.
	 * @param s - The string to check.
	 * @return Returns {@code true} if the specified string says true or yes.
	 */
	public static boolean isTrueOrYes(final String s){
		return s.matches("[tT]rue|[yY]es|y");
	}

	/**
	 * Checks if the given string says false or no.
	 * @param s - The string to check.
	 * @return Returns {@code true} if the specified string says false or no.
	 */
	public static boolean isFalseOrNo(final String s){
		return s.matches("[fF]false|[nN]o|n");
	}

	/**
	 * Converts all strings in the given array into one single string.
	 * @param between - What should be added between the strings. Can be {@code null}.
	 * @param array - The array whose strings get converted into one. If the array is {@code null} or empty, an empty string will be returned.
	 * @return Returns a single string containing all of the strings of the given array.
	 */
	public static String join(@Nullable final String between, @Nullable final String... array) {
		if (array != null && array.length > 0) {
			if (array.length == 1) {
				return array[0];
			}
			else {
				final StringBuilder b = new StringBuilder();
				final boolean useBetween = between != null && !between.isEmpty();
				for (final String s : array) {
					if (useBetween) { b.append(s).append(between); }
					else { b.append(s); }
				}
				return useBetween ? b.delete(b.length()-between.length(), b.length()).toString().trim() : b.toString().trim();
			}
		}
		return "";
	}

	/**
	 * Converts all strings in the given array into one single string.
	 * @param operator - A operator that is able to modify every string in the array right before being appended to the string.
	 * @param between - What should be added between the strings. Can be {@code null}.
	 * @param array - The array whose strings get converted into one. If the array is {@code null} or empty, an empty string will be returned.
	 * @return Returns a single string containing all of the strings of the given array.
	 */
	public static String join(final UnaryOperator<String> operator, @Nullable final String between, @Nullable final String... array) {
		if (array != null && array.length > 0) {
			if (array.length == 1) {
				return array[0];
			}
			else {
				final StringBuilder b = new StringBuilder();
				final boolean useBetween = between != null && !between.isEmpty();
				for (String s : array) {
					s = operator.apply(s);
					if (useBetween) { b.append(s).append(between); }
					else { b.append(s); }
				}
				return useBetween ? b.delete(b.length()-between.length(), b.length()).toString().trim() : b.toString().trim();
			}
		}
		return "";
	}

	/**
	 * Converts all strings in the given array between the start and stop index into one single string.
	 *
	 * <p>
	 * For example:
	 * <pre> String[] myArray = new String[]{"hello", "how", "are", "you"};
	 * System.out.print( StringUtils.join(" ", 1, 3, myArray) ); </pre>
	 * Will produce the following output: {@code "how are"}
	 * <p>
	 *
	 * @param between - What should be added between the strings. Can be {@code null}.
	 * @param start - Where to start in the given array. (including the string at the given index)
	 * @param stop - Where to stop in the given array. (excluding the string at the given index)
	 * @param array - The array whose strings get converted into one.
	 * @return Returns a single string containing all of the strings between the given start and stop position from the array.
	 */
	public static String join(@Nullable final String between, final int start, final int stop, final String... array) {
		final StringBuilder b = new StringBuilder();
		final boolean useBetween = between != null && !between.isEmpty();
		for (int i=start; i<stop; i++) {
			final String string = array[i];
			if (useBetween) { b.append(string).append(between); }
			else { b.append(string); }
		}
		return b.toString().trim();
	}

	/**
	 * Converts all strings in the given array between the start and stop index into one single string.
	 *
	 * <p>
	 * For example:
	 * <pre> String[] myArray = new String[]{"hello", "how", "are", "you"};
	 * System.out.print( StringUtils.join(" ", 1, 3, myArray) ); </pre>
	 * Will produce the following output: {@code "how are"}
	 * <p>
	 *
	 * @param operator - A operator that is able to modify every string in the array right before being appended to the string.
	 * @param between - What should be added between the strings. Can be {@code null}.
	 * @param start - Where to start in the given array. (including the string at the given index)
	 * @param stop - Where to stop in the given array. (excluding the string at the given index)
	 * @param array - The array whose strings get converted into one.
	 * @return Returns a single string containing all of the strings between the given start and stop position from the array.
	 */
	public static String join(final UnaryOperator<String> operator, @Nullable final String between, final int start, final int stop, final String... array) {
		final StringBuilder b = new StringBuilder();
		final boolean useBetween = between != null && !between.isEmpty();
		for (int i=start; i<stop; i++) {
			String string = array[i];
			string = operator.apply(string);
			if (useBetween) { b.append(string).append(between); }
			else { b.append(string); }
		}
		return b.toString().trim();
	}

	/**
	 * Returns the given string if it is non-null; the empty string otherwise.
	 *
	 * @param string the string to test and possibly return
	 * @return {@code string} itself if it is non-null; {@code ""} if it is null
	 * @see Strings#nullToEmpty(String)
	 */
	public static String nullToEmpty(@Nullable final String string) {
		return Strings.nullToEmpty(string);
	}

	/**
	 * Returns the given string if it is nonempty; {@code null} otherwise.
	 *
	 * @param string the string to test and possibly return
	 * @return {@code string} itself if it is nonempty; {@code null} if it is
	 *     empty or null
	 * @see Strings#emptyToNull(String)
	 */
	@Nullable public static String emptyToNull(@Nullable final String string) {
		return Strings.emptyToNull(string);
	}

	/**
	 * Returns {@code true} if the given string is null or is the empty string.
	 *
	 * <p>Consider normalizing your string references with {@link #nullToEmpty}.
	 * If you do, you can use {@link String#isEmpty()} instead of this
	 * method, and you won't need special null-safe forms of methods like {@link
	 * String#toUpperCase} either. Or, if you'd like to normalize "in the other
	 * direction," converting empty strings to {@code null}, you can use {@link
	 * #emptyToNull}.
	 *
	 * @param string a string reference to check
	 * @return {@code true} if the string is null or is the empty string
	 * @see Strings#isNullOrEmpty(String)
	 */
	public static boolean isNullOrEmpty(@Nullable final String string) {
		return Strings.isNullOrEmpty(string);
	}

	/**
	 * Returns a string, of length at least {@code minLength}, consisting of
	 * {@code string} prepended with as many copies of {@code padChar} as are
	 * necessary to reach that length. For example,
	 *
	 * <ul>
	 * <li>{@code padStart("7", 3, '0')} returns {@code "007"}
	 * <li>{@code padStart("2010", 3, '0')} returns {@code "2010"}
	 * </ul>
	 *
	 * <p>See {@link Formatter} for a richer set of formatting capabilities.
	 *
	 * @param string the string which should appear at the end of the result
	 * @param minLength the minimum length the resulting string must have. Can be
	 *     zero or negative, in which case the input string is always returned.
	 * @param padChar the character to insert at the beginning of the result until
	 *     the minimum length is reached
	 * @return the padded string
	 * @see Strings#padStart(String, int, char)
	 */
	public static String padStart(final String string, final int minLength, final char padChar) {
		return Strings.padStart(string, minLength, padChar);
	}

	/**
	 * Returns a string, of length at least {@code minLength}, consisting of
	 * {@code string} appended with as many copies of {@code padChar} as are
	 * necessary to reach that length. For example,
	 *
	 * <ul>
	 * <li>{@code padEnd("4.", 5, '0')} returns {@code "4.000"}
	 * <li>{@code padEnd("2010", 3, '!')} returns {@code "2010"}
	 * </ul>
	 *
	 * <p>See {@link Formatter} for a richer set of formatting capabilities.
	 *
	 * @param string the string which should appear at the beginning of the result
	 * @param minLength the minimum length the resulting string must have. Can be
	 *     zero or negative, in which case the input string is always returned.
	 * @param padChar the character to append to the end of the result until the
	 *     minimum length is reached
	 * @return the padded string
	 * @see Strings#padEnd(String, int, char)
	 */
	public static String padEnd(final String string, final int minLength, final char padChar) {
		return Strings.padEnd(string, minLength, padChar);
	}

	/**
	 * Returns a string consisting of a specific number of concatenated copies of
	 * an input string. For example, {@code repeat("hey", 3)} returns the string
	 * {@code "heyheyhey"}.
	 *
	 * @param string any non-null string
	 * @param count the number of times to repeat it; a nonnegative integer
	 * @return a string containing {@code string} repeated {@code count} times
	 *     (the empty string if {@code count} is zero)
	 * @throws IllegalArgumentException if {@code count} is negative
	 * @see Strings#repeat(String, int)
	 */
	public static String repeat(final String string, final int count) {
		return Strings.repeat(string, count);
	}

	/**
	 * Returns a string consisting of a specific number of concatenated copies of
	 * an input character. For example, {@code repeat("hey", 3)} returns the string
	 * {@code "heyheyhey"}.
	 *
	 * @param character any non-null character
	 * @param count the number of times to repeat it; a nonnegative integer
	 * @return a string containing {@code string} repeated {@code count} times
	 *     (the empty string if {@code count} is zero)
	 * @throws IllegalArgumentException if {@code count} is negative
	 * @see Strings#repeat(String, int)
	 */
	public static String repeat(final Character character, final int count) {
		return Strings.repeat(character.toString(), count);
	}

	/**
	 * Returns the longest string {@code prefix} such that
	 * {@code a.toString().startsWith(prefix) && b.toString().startsWith(prefix)},
	 * taking care not to split surrogate pairs. If {@code a} and {@code b} have
	 * no common prefix, returns the empty string.
	 *
	 * @since 11.0
	 * @see Strings#commonPrefix(CharSequence, CharSequence)
	 */
	@Beta
	public static String commonPrefix(final CharSequence a, final CharSequence b) {
		return Strings.commonPrefix(a, b);
	}

	/**
	 * Returns the longest string {@code suffix} such that
	 * {@code a.toString().endsWith(suffix) && b.toString().endsWith(suffix)},
	 * taking care not to split surrogate pairs. If {@code a} and {@code b} have
	 * no common suffix, returns the empty string.
	 *
	 * @since 11.0
	 * @see Strings#commonSuffix(CharSequence, CharSequence)
	 */
	@Beta
	public static String commonSuffix(final CharSequence a, final CharSequence b) {
		return Strings.commonSuffix(a, b);
	}

	/**
	 * Checks whether or not the given string is empty.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is empty or {@code null} and {@code false} if not.
	 */
	public static final boolean isEmpty(final String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * Checks whether or not the given stringbuilder is empty.
	 * @param sb - The stringbuilder to check.
	 * @return Returns {@code true} if the stringbuilder is empty or {@code null}.
	 */
	public static final boolean isEmpty(final StringBuilder sb) {
		return sb == null || sb.length() < 1;
	}

	/**
	 * Checks whether or not the given string is a word.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a word and {@code false} if not.
	 */
	public static final boolean isWord(final String s) {
		return s != null && s.matches("(?i)[a-zäöüß]+");
	}

	/**
	 * Checks whether or not the given string is a command.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a command and {@code false} if not.
	 */
	public static final boolean isCommandWord(final String s) {
		return s != null && s.matches("(?i)/[a-z]+");
	}

	/**
	 * Checks whether or not the given string is a website.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a website and {@code false} if not.
	 */
	public static final boolean isWebsite(final String s) {
		return s != null
				&& s.matches("(?i)(http(s)?://)?([a-z0-9-_äöüß]+\\.)+(a-z){2,4}((\\?[a-z0-9-_äöüß](=[a-z0-9-_äöüß]*)?)|(/[a-z0-9-_äöüß]*))*");
	}

	/**
	 * Checks whether or not the given string could be a minecraft username.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string could be a minecraft username and {@code false} if not.
	 */
	public static final boolean isMinecraftname(final String s) {
		return s != null && s.matches("(?i)[0-9a-z_]{3,16}");
	}

	/**
	 * Checks whether or not the given string is a rank.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a rank and {@code false} if not.
	 */
	public static final boolean isRank(final String s) {
		return s != null &&
				s.matches("(Admin)|(Moderator)|(Veteran)|(Vip)|(Trusted)|(Spieler)|(Player)");
	}

	/**
	 * Checks whether or not the given string is a unformated {@link UUID}.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a unformated uniqueid and {@code false} if not.
	 */
	public static final boolean isUnformatedUUID(final String s) {
		return s != null && !s.isEmpty() && s.matches("[0-9a-f]{32}");
	}

	/**
	 * Checks whether or not the given string is a {@link UUID}.
	 * @param s - The {@link String} to check.
	 * @return Returns {@code true} if the string is a uniqueid and {@code false} if not.
	 */
	public static final boolean isUUID(final String s) {
		if(s != null && !s.isEmpty() && s.matches("(?i)([0-9a-f]){8}-([0-9a-f]){4}-([0-9a-f]){4}-([0-9a-f]){4}-([0-9a-f]){12}")) {
			try {
				UUID.fromString(s);
				return true;
			} catch (final Exception ignore) { }
		}
		return false;
	}
}
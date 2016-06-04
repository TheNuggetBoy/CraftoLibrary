package de.craftolution.craftolibrary;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 07.05.2016
 */
public class ObjectJsonConverter {

	private static char[] getSpaces(final int level) {
		final char[] c = new char[level * 3];
		for (int i = 0; i < level * 3; i++) {
			c[i] = ' ';
		}
		return c;
	}

	/** TODO: Documentation */
	public static StringBuilder toJson(final Object object) throws IllegalArgumentException, IllegalAccessException {
		final StringBuilder b = new StringBuilder();
		b.append('{');
		ObjectJsonConverter.appendToString(b, object, 0, new HashSet<>());
		b.append('}');
		return b;
	}

	private static StringBuilder appendToString(final StringBuilder b, final Object object, int level, final Set<Integer> printedObjects) throws IllegalArgumentException, IllegalAccessException {
		b.append(ObjectJsonConverter.getSpaces(level));

		b.append('\'').append(object.getClass().getSimpleName()).append("@").append(Integer.toHexString(object.hashCode())).append("': \n").append(ObjectJsonConverter.getSpaces(level)).append('{');

		level += 1;

		for (int fieldIndex = 0; fieldIndex < object.getClass().getDeclaredFields().length; fieldIndex++) {
			final Field field = object.getClass().getDeclaredFields()[fieldIndex];
			field.setAccessible(true);

			if (field.isAnnotationPresent(JsonHidden.class)) {
				continue;
			}

			final Object value = field.get(object);

			b.append('\n').append(ObjectJsonConverter.getSpaces(level)).append('\'').append(field.getName()).append("': ");

			if (value != null) {
				//System.out.println("Appending value: " + field.getName() + "@" + field.get(object).hashCode()+" (" + field.getType() + ") in class " + object.getClass().getName());
				if (ObjectJsonConverter.isSimple(field.getType()) || !printedObjects.contains(value.hashCode())) {
					ObjectJsonConverter.appendValue(b, field.getType(), value, level+1, printedObjects);
				}
				else { b.append('\'').append(field.getType().getName()).append('@').append(Integer.toHexString(value.hashCode())).append('\''); }
			}
			else { b.append("'null'"); }

			if (fieldIndex != object.getClass().getDeclaredFields().length - 1) { b.append(','); }
		}

		b.append('\n').append(ObjectJsonConverter.getSpaces(level - 1)).append("}");

		printedObjects.add(object.hashCode());

		return b;
	}

	private static StringBuilder appendValue(final StringBuilder b, final Class<?> type, final Object value, final int level, final Set<Integer> printedObjects) throws IllegalArgumentException, IllegalAccessException {
		if (type == null || value == null) {
			b.append("'null'");
		}
		else if (ObjectJsonConverter.isSimple(type)) { b.append("'").append(value).append("'"); }
		else if (type.isArray()) {
			b.append("[");

			if (Array.getLength(value) > 0) {
				//Object[] array = (Object[]) value;
				boolean newLine = false;

				for (int i = 0; i < Array.getLength(value); i++) {
					final Object arrayField = Array.get(value, i);

					if (!newLine && arrayField != null && !ObjectJsonConverter.isSimple(arrayField.getClass())) {
						newLine = true;
					}

					//Object arrayField = array[i];
					if (i != 0) { b.append(", "); }

					if (arrayField == null) { b.append("'null'"); }
					else {
						ObjectJsonConverter.appendValue(b, arrayField.getClass(), arrayField, level + 1, printedObjects);
					}
				}

				if (newLine) { b.append('\n').append(ObjectJsonConverter.getSpaces(level - 1)); }
			}

			b.append("]");
		}
		else {
			if (!printedObjects.contains(value.hashCode())) {
				printedObjects.add(value.hashCode());
				b.append('\n').append(ObjectJsonConverter.getSpaces(level -1)).append("{\n");
				ObjectJsonConverter.appendToString(b, value, level, printedObjects);
				b.append("\n").append(ObjectJsonConverter.getSpaces(level - 1)).append("}");
			}
			else { b.append('\'').append(type.getName()).append('@').append(Integer.toHexString(value.hashCode())).append('\''); }
		}

		return b;
	}

	private static boolean isSimple(final Class<?> type) {
		return type == null || type.isPrimitive() || type.equals(String.class) || type.equals(Integer.class) || type.equals(Short.class) || type.equals(Long.class)
				|| type.equals(Float.class) || type.equals(Double.class) || type.equals(Boolean.class) || type.equals(Character.class) || type.equals(BigInteger.class);
	}

}
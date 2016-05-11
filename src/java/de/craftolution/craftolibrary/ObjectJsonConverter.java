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

	private static char[] getSpaces(int level) {
		char[] c = new char[level * 3];
		for (int i = 0; i < level * 3; i++) {
			c[i] = ' ';
		}
		return c;
	}

	/** TODO: Documentation */
	public static StringBuilder toJson(Object object) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder b = new StringBuilder();
		b.append('{');
		appendToString(b, object, 0, new HashSet<>());
		b.append('}');
		return b;
	}

	private static StringBuilder appendToString(StringBuilder b, Object object, int level, Set<Integer> printedObjects) throws IllegalArgumentException, IllegalAccessException {
		b.append(getSpaces(level));
		
		b.append('\'').append(object.getClass().getSimpleName()).append("@").append(Integer.toHexString(object.hashCode())).append("': \n").append(getSpaces(level)).append('{');
		
		level += 1;
		
		for (int fieldIndex = 0; fieldIndex < object.getClass().getDeclaredFields().length; fieldIndex++) {
			Field field = object.getClass().getDeclaredFields()[fieldIndex];
			field.setAccessible(true);
			
			if (field.isAnnotationPresent(JsonHidden.class)) {
				continue;
			}
			
			Object value = field.get(object);
			
			b.append('\n').append(getSpaces(level)).append('\'').append(field.getName()).append("': ");
			
			if (value != null) {
				//System.out.println("Appending value: " + field.getName() + "@" + field.get(object).hashCode()+" (" + field.getType() + ") in class " + object.getClass().getName());
				if (isSimple(field.getType()) || !printedObjects.contains(value.hashCode())) {
					appendValue(b, field.getType(), value, level+1, printedObjects);
				}
				else { b.append('\'').append(field.getType().getName()).append('@').append(Integer.toHexString(value.hashCode())).append('\''); }
			}
			else { b.append("'null'"); }
			
			if (fieldIndex != object.getClass().getDeclaredFields().length - 1) { b.append(','); }
		}

		b.append('\n').append(getSpaces(level - 1)).append("}");
		
		printedObjects.add(object.hashCode());
		
		return b;
	}
	
	private static StringBuilder appendValue(StringBuilder b, Class<?> type, Object value, int level, Set<Integer> printedObjects) throws IllegalArgumentException, IllegalAccessException {
		if (type == null || value == null) {
			b.append("'null'");
		}
		else if (isSimple(type)) { b.append("'").append(value).append("'"); }
		else if (type.isArray()) {
			b.append("[");

			if (Array.getLength(value) > 0) {
				//Object[] array = (Object[]) value;
				boolean newLine = false;

				for (int i = 0; i < Array.getLength(value); i++) {
					Object arrayField = Array.get(value, i);
					
					if (!newLine && arrayField != null && !isSimple(arrayField.getClass())) {
						newLine = true;
					}

					//Object arrayField = array[i];
					if (i != 0) { b.append(", "); }
					
					if (arrayField == null) { b.append("'null'"); }
					else {
						appendValue(b, arrayField.getClass(), arrayField, level + 1, printedObjects);
					}
				}
				
				if (newLine) { b.append('\n').append(getSpaces(level - 1)); }
			}
			
			b.append("]");
		}
		else {
			if (!printedObjects.contains(value.hashCode())) {
				printedObjects.add(value.hashCode());
				b.append('\n').append(getSpaces(level -1)).append("{\n");
				appendToString(b, value, level, printedObjects);
				b.append("\n").append(getSpaces(level - 1)).append("}");
			}
			else { b.append('\'').append(type.getName()).append('@').append(Integer.toHexString(value.hashCode())).append('\''); }
		}
		
		return b;
	}

	private static boolean isSimple(Class<?> type) {
		return type == null || type.isPrimitive() || type.equals(String.class) || type.equals(Integer.class) || type.equals(Short.class) || type.equals(Long.class) 
				|| type.equals(Float.class) || type.equals(Double.class) || type.equals(Boolean.class) || type.equals(Character.class) || type.equals(BigInteger.class);
	}

}
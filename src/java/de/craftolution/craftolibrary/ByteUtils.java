package de.craftolution.craftolibrary;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class ByteUtils {

//	/** TODO: Documentation */
//	public static int threeBytesToInt(final byte[] b) {
//		return b[2] & 0xFF | (b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 16;
//	}
//
//	/** TODO: Documentation */
//	public static byte[] intToThreeBytes(final int i) {
//		return new byte[] { (byte) (i >> 16 & 0xFF), (byte) (i >> 8 & 0xFF), (byte) (i & 0xFF) };
//	}

	public static String displayBytes(byte... byteArray) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : byteArray) {
			builder.append(((int) b)).append(' ');
		}
		return builder.toString().trim();
	}
	
	/** TODO: Documentation */
	public static String displayBits(byte... byteArray) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : byteArray) {
			builder.append(Integer.toBinaryString(b)).append(' ');
		}
		return builder.toString().trim();
	}

	/** TODO: Documentation */
	public static int bytesToInt(byte... array) {
		int result = 0;
		for (int i = 0; i < array.length; i++) {
			result = (result << 8) + (array[i] & 0xFF);
		}
		return result;
	}

	/** TODO: Documentation */
	public static byte[] intToBytes(int number) {
		return new byte[] { (byte) (number >> 24 & 0xFF), (byte) (number >> 16 & 0xFF), (byte) (number >> 8 & 0xFF), (byte) (number & 0xFF)};
	}

	/** TODO: Documentation */
	public static byte[] intToBytes(int number, int arrayLength) {
		byte[] bytes = new byte[arrayLength];
		int shifted = arrayLength * 8 - 8;
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (number >> shifted & 0xFF);
			shifted -= 8;
		}
		return bytes;
	}

	public static void main(String[] args) {
		int i = 5;

		byte[] array = intToBytes(i);
		for (byte b : array) {
			System.out.print(Integer.toBinaryString(b) + " ");
		}

		int result = bytesToInt(array);
		System.out.println("\n" + result);
	}

}
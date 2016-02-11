package de.craftolution.craftolibrary.craftocore;

import de.craftolution.craftolibrary.ByteUtils;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class Test {

	public static void main(final String[] args) {
		Test.testPackets();
	}

	static void testPackets() {
		final Packet p = Packet.builder().service(3).type(PacketTypes.ASK_TUNNEL).packetId(27438).content("Ok.").build();
		final byte[] bytes = p.toByteArray();

		System.out.println((byte) 8);

		System.out.println(ByteUtils.displayBytes(bytes));
	}
}
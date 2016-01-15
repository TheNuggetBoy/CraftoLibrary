package de.craftolution.craftolibrary.craftocore;

import de.craftolution.craftolibrary.ByteUtils;
import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class Packet {

	static final int PACKET_LENGTH_BYTES = 3;
	static final int PACKET_SENDER_BYTES = 1;
	static final int PACKET_TYPE_BYTES = 1;
	static final int PACKET_ID_BYTES = 3;

	/** TODO: Documentation */
	public static PacketBuilder builder() { return new PacketBuilder(); }

	/** TODO: Documentation */
	public static Packet ofBytes(byte... bytes) {
		
	}

	private final byte[] packetLength;
	private final byte serviceId;
	private final byte typeId;
	private final byte[] packetId;
	private final byte[] content;

	Packet(final byte[] packetLength, final byte serviceId, final byte typeId, final byte[] packetId, final byte[] content) {
		Check.notNull("The packetIdarray/contentarray cannot be null!", packetId, content);
		this.packetLength = packetLength;
		this.serviceId = serviceId;
		this.typeId = typeId;
		this.packetId = packetId;
		this.content = content;
	}

	/** TODO: Documentation */
	public int getServiceId() { return this.serviceId; }

	/** TODO: Documentation */
	public int getTypeId() { return this.typeId; }

	/** TODO: Documentation */
	public int getPacketId() { return ByteUtils.bytesToInt(this.packetId); }

	/** TODO: Documentation */
	public byte[] getContent() { return this.content; }

	public byte[] toByteArray() {
		final byte[] bytes = new byte[Packet.PACKET_LENGTH_BYTES + this.packetLength];
		int lastIndex = 0;

		// Packetlength
		System.arraycopy(lengthArray, 0, bytes, lastIndex, Packet.PACKET_LENGTH_BYTES);
		lastIndex += Packet.PACKET_LENGTH_BYTES;

		// Service id
		bytes[lastIndex++] = this.serviceId;

		// Packet type
		bytes[lastIndex++] = this.typeId;

		// Packet id
		System.arraycopy(this.packetId, 0, bytes, lastIndex, Packet.PACKET_ID_BYTES);
		lastIndex += Packet.PACKET_ID_BYTES;

		// Content
		System.arraycopy(this.content, 0, bytes, lastIndex, this.content.length);
		lastIndex += this.content.length;

		return bytes;
	}

}
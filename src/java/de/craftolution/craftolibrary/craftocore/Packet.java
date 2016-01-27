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

	static final int PACKET_LENGTH_BYTECOUNT = 3;
	static final int PACKET_SENDER_BYTECOUNT = 1;
	static final int PACKET_TYPE_BYTECOUNT = 1;
	static final int PACKET_ID_BYTECOUNT = 3;
	static final int PACKET_LENGTH_LIMIT = 1048576;

	/** TODO: Documentation */
	public static PacketBuilder builder() { return new PacketBuilder(); }

	/** TODO: Documentation */
	public static Packet ofBytes(final byte... bytes) throws InvalidPacketException {
		if (bytes.length > PACKET_LENGTH_LIMIT) { 
			throw new InvalidPacketException("The byte array is bigger than the maximum allowed bytecount! (" + bytes.length + " > " + PACKET_LENGTH_LIMIT + ")"); 
		}

		int index = 0;

		final byte[] packetLength = new byte[Packet.PACKET_LENGTH_BYTECOUNT];
		System.arraycopy(bytes, 0, packetLength, 0, Packet.PACKET_LENGTH_BYTECOUNT);
		index += Packet.PACKET_LENGTH_BYTECOUNT;

		final byte serviceId = bytes[index++];

		final byte typeId = bytes[index++];

		final byte[] packetId = new byte[Packet.PACKET_ID_BYTECOUNT];
		System.arraycopy(bytes, index, packetId, 0, Packet.PACKET_ID_BYTECOUNT);
		index += Packet.PACKET_ID_BYTECOUNT;

		final byte[] content = new byte[bytes.length - Packet.PACKET_ID_BYTECOUNT - Packet.PACKET_TYPE_BYTECOUNT - Packet.PACKET_SENDER_BYTECOUNT - Packet.PACKET_LENGTH_BYTECOUNT];
		System.arraycopy(bytes, index, content, 0, content.length);

		return new Packet(packetLength, serviceId, typeId, packetId, content);
	}

	private final byte[] packetLength;
	private final byte serviceId;
	private final byte typeId;
	private final byte[] packetId;
	private final byte[] content;

	Packet(final byte[] packetLength, final byte serviceId, final byte typeId, final byte[] packetId, final byte[] content) throws InvalidPacketException {
		Check.notNull("The packetIdarray/contentarray cannot be null!", packetId, content);
		if (packetLength.length != Packet.PACKET_LENGTH_BYTECOUNT) { 
			throw new InvalidPacketException("The packetLength-array doesnt match the PACKET_LENGTH_BYTECOUNT constant! (" + packetLength.length + " != " + PACKET_LENGTH_BYTECOUNT + ")");
		}
		if (packetId.length != Packet.PACKET_ID_BYTECOUNT) {
			throw new InvalidPacketException("The packetID-array doesnt match the PACKET_ID_BYTECOUNT constant! (" + packetId.length + " != " + PACKET_ID_BYTECOUNT +")");
		}

		this.packetLength = packetLength;
		this.serviceId = serviceId;
		this.typeId = typeId;
		this.packetId = packetId;
		this.content = content;
	}

	/** TODO: Documentation */
	public int getServiceId() { return this.serviceId; }

	/** TODO: Documentation */
	public PacketType getType() { return PacketTypes.valueOf(this.typeId).orElseThrow(() -> new InvalidPacketException("The packettype cannot be resolved: " + this.typeId)); }

	/** TODO: Documentation */
	public int getTypeId() { return this.typeId; }

	/** TODO: Documentation */
	public int getPacketId() { return ByteUtils.bytesToInt(this.packetId); }

	/** TODO: Documentation */
	public byte[] getContent() { return this.content; }

	/** TODO: Documentation */
	public byte[] toByteArray() {
		final byte[] bytes = new byte[Packet.PACKET_LENGTH_BYTECOUNT + this.packetLength.length];
		int lastIndex = 0;

		// Packetlength
		System.arraycopy(this.packetLength, 0, bytes, lastIndex, Packet.PACKET_LENGTH_BYTECOUNT);
		lastIndex += Packet.PACKET_LENGTH_BYTECOUNT;

		// Service id
		bytes[lastIndex++] = this.serviceId;

		// Packet type
		bytes[lastIndex++] = this.typeId;

		// Packet id
		System.arraycopy(this.packetId, 0, bytes, lastIndex, Packet.PACKET_ID_BYTECOUNT);
		lastIndex += Packet.PACKET_ID_BYTECOUNT;

		// Content
		System.arraycopy(this.content, 0, bytes, lastIndex, this.content.length);
		lastIndex += this.content.length;

		return bytes;
	}

}
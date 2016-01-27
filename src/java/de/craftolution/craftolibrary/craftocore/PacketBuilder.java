package de.craftolution.craftolibrary.craftocore;

import de.craftolution.craftolibrary.Builder;
import de.craftolution.craftolibrary.ByteUtils;
import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class PacketBuilder implements Builder<Packet> {

	private byte serviceId;
	private byte typeId;
	private byte[] packetId;
	private byte[] content;

	/** TODO: Documentation */
	public PacketBuilder() { }

	/** TODO: Documentation */
	public PacketBuilder service(final byte serviceId) { this.serviceId = serviceId; return this; }
	/** TODO: Documentation */
	public PacketBuilder service(final int serviceId) { return this.service((byte) serviceId); }


	/** TODO: Documentation */
	public PacketBuilder type(final byte typeId) { this.typeId = typeId; return this; }
	/** TODO: Documentation */
	public PacketBuilder type(final PacketType typeId) { return this.type(typeId.getId()); }

	/** TODO: Documentation */
	public PacketBuilder packetId(final byte[] packetId) { this.packetId = packetId; return this; }
	/** TODO: Documentation */
	public PacketBuilder packetId(final int packetId) { return this.packetId(ByteUtils.intToBytes(packetId, Packet.PACKET_ID_BYTECOUNT)); }

	/** TODO: Documentation */
	public PacketBuilder content(final byte... content) { this.content = content; return this; }
	/** TODO: Documentation */
	public PacketBuilder content(final String content) { return this.content(content.getBytes()); }

	/** TODO: Documentation */
	@Override
	public Packet build() throws InvalidPacketException, IllegalArgumentException {
		Check.notNull("The packetIdarray/contentarray cannot be null!", this.packetId, this.content);
		final byte[] packetLength = ByteUtils.intToBytes(Packet.PACKET_SENDER_BYTECOUNT + Packet.PACKET_TYPE_BYTECOUNT + Packet.PACKET_ID_BYTECOUNT + this.content.length, Packet.PACKET_LENGTH_BYTECOUNT);
		return new Packet(packetLength, this.serviceId, this.typeId, this.packetId, this.content);
	}

}
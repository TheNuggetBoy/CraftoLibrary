package de.craftolution.craftolibrary.craftocore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class PacketTypes {

	private static final HashMap<Byte, PacketType> typeMap = new HashMap<>();

	/** TODO: Documentation */
	public static final PacketType CONNECT = PacketTypes.reg(0x00);
	/** TODO: Documentation */
	public static final PacketType DISCONNECT = PacketTypes.reg(0x01);
	/** TODO: Documentation */
	public static final PacketType SUBSCRIBE = PacketTypes.reg(0x02);
	/** TODO: Documentation */
	public static final PacketType UNSUBSCRIBE = PacketTypes.reg(0x03);
	/** TODO: Documentation */
	public static final PacketType LIST_SERVICES = PacketTypes.reg(0x04);
	/** TODO: Documentation */
	public static final PacketType ASK_TUNNEL = PacketTypes.reg(0x05);
	/** TODO: Documentation */
	public static final PacketType SYNC_TIME = PacketTypes.reg(0x06);
	/** TODO: Documentation */
	public static final PacketType NOTHING = PacketTypes.reg(0x07);

	private static PacketType reg(final int id) {
		final PacketType type = new PacketType(id);
		PacketTypes.register(type);
		return type;
	}

	/** TODO: Documentation */
	public static void register(final PacketType type) throws IllegalArgumentException {
		if (PacketTypes.typeMap.containsKey(type.getId())) {
			throw new IllegalArgumentException("The packettypes map already contains a packet (" + type.toString() + ") with the specified id: " + type.getId());
		}
		PacketTypes.typeMap.put(type.getId(), type);
	}

	/** TODO: Documentation */
	public static Optional<PacketType> valueOf(final byte id) { return Optional.ofNullable(PacketTypes.typeMap.get(id)); }

	/** TODO: Documentation */
	public static Collection<PacketType> values() { return PacketTypes.typeMap.values(); }

}
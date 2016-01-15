package de.craftolution.craftolibrary.craftocore;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class PacketType {

	private final byte typeId;

	PacketType(final int typeId) {
		this.typeId = (byte) typeId;
	}

	/** TODO: Documentation */
	public byte getId() { return this.typeId; }

}
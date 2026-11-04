package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomMap extends PSSendPacket {
    private final int mapCode;
    private final int playerCount;
    private final int roundId;
    private final byte[] mapXml;
    private final String mapAuthor;
    private final int mapCategory;
    private final boolean isMirrored;
    private final boolean hasEnabledConjuration;
    private final boolean hasEnabledMiceCollisions;
    private final boolean hasEnabledFallDamage;
    private final int miceWeight;

    /**
     * Creates a new packet of SEND_ROOM_MAP.
     * @param mapCode The room's map code.
     * @param playerCount The room's player count.
     * @param roundId The room's round id.
     * @param mapXml The room's map XML code.
     * @param mapAuthor The room's map author.
     * @param mapCategory The room's map category.
     * @param isMirrored True if the room's map is mirrored or else False.
     * @param hasEnabledConjuration True if the room's map has enabled conjuration or else False.
     * @param hasEnabledMiceCollisions True if the room's map has enabled player collisions or else False.
     * @param hasEnabledFallDamage True if the room's map has enabled fall damage or else False.
     * @param miceWeight The room's player weight.
     */
    public PacketSendRoomMap(int mapCode, int playerCount, int roundId, byte[] mapXml, String mapAuthor, int mapCategory, boolean isMirrored, boolean hasEnabledConjuration, boolean hasEnabledFallDamage, boolean hasEnabledMiceCollisions, int miceWeight) {
        super(PacketOpcodes.SEND_ROOM_MAP);
        this.mapCode = mapCode;
        this.playerCount = playerCount;
        this.roundId = roundId;
        this.mapXml = mapXml;
        this.mapAuthor = mapAuthor;
        this.mapCategory = mapCategory;
        this.isMirrored = isMirrored;
        this.hasEnabledConjuration = hasEnabledConjuration;
        this.hasEnabledFallDamage = hasEnabledFallDamage;
        this.hasEnabledMiceCollisions = hasEnabledMiceCollisions;
        this.miceWeight = miceWeight;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.mapCode);
        packet.writeShort((short) this.playerCount);
        packet.writeByte(this.roundId);
        packet.writeInt(this.mapXml.length);
        packet.writeBytes(this.mapXml);
        packet.writeUTF(this.mapAuthor, true);
        packet.writeByte(this.mapCategory);
        packet.writeBoolean(this.isMirrored);
        packet.writeBoolean(this.hasEnabledConjuration);
        packet.writeBoolean(this.hasEnabledMiceCollisions);
        packet.writeBoolean(this.hasEnabledFallDamage);
        packet.writeInt(this.miceWeight);
        return packet;
    }
}
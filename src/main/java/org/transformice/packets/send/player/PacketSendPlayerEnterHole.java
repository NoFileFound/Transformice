package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerEnterHole extends PSSendPacket {
    private final int sessionId;
    private final int type;
    private final short score;
    private final int place;
    private final int time;

    /**
     * Creates a new packet of SEND_PLAYER_ENTER_HOLE.
     * @param sessionId The player's session id.
     * @param type The hole's type.
     * @param score The player's score.
     * @param place The player's place.
     * @param time The player when entered the hole.
     */
    public PacketSendPlayerEnterHole(int sessionId, int type, short score, int place, int time) {
        super(PacketOpcodes.SEND_PLAYER_ENTER_HOLE);
        this.sessionId = sessionId;
        this.type = type;
        this.score = score;
        this.place = place;
        this.time = time;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.type).writeInt(this.sessionId).writeShort(this.score).writeByte(this.place).writeUnsignedShort(this.time);
    }
}
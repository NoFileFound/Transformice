package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerMeep extends PSSendPacket {
    private final int sessionId;
    private final int x;
    private final int y;
    private final int power;

    /**
     * Creates a new packet of SEND_PLAYER_MEEP.
     * @param sessionId The player's session.
     * @param x The X-position.
     * @param y The Y-position.
     * @param power The meep power.
     */
    public PacketSendPlayerMeep(int sessionId, int x, int y, int power) {
        super(PacketOpcodes.SEND_PLAYER_MEEP);
        this.sessionId = sessionId;
        this.x = x;
        this.y = y;
        this.power = power;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort((short) this.x).writeShort((short) this.y).writeInt(this.power);
    }
}
package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerSetIceCubed extends PSSendPacket {
    private final int sessionId;
    private final int freezeType;
    private final int seconds;

    /**
     * Creates a new packet of SEND_PLAYER_SET_ICE_CUBED.
     * @param sessionId The player's session id.
     * @param freezeType The freeze type.
     * @param seconds The freeze seconds.
     */
    public PacketSendPlayerSetIceCubed(int sessionId, int freezeType, int seconds) {
        super(PacketOpcodes.SEND_PLAYER_SET_ICE_CUBED);
        this.sessionId = sessionId;
        this.freezeType = freezeType;
        this.seconds = seconds;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeByte(this.freezeType).writeShort((short) this.seconds);
    }
}
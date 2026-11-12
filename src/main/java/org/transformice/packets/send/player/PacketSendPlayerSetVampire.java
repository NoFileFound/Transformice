package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerSetVampire extends PSSendPacket {
    private final int sessionId;
    private final boolean isVampire;
    private final boolean isTransmissible;

    /**
     * Creates a new packet of SEND_PLAYER_SET_VAMPIRE.
     * @param sessionId The player's session id.
     * @param isTransmissible Is transmissible.
     */
    public PacketSendPlayerSetVampire(int sessionId, boolean isVampire, boolean isTransmissible) {
        super(PacketOpcodes.SEND_PLAYER_SET_VAMPIRE);
        this.sessionId = sessionId;
        this.isVampire = isVampire;
        this.isTransmissible = isTransmissible;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isVampire).writeBoolean(this.isTransmissible);
    }
}
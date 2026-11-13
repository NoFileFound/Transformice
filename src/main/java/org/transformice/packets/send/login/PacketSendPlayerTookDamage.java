package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerTookDamage extends PSSendPacket {
    private final int playerSessionId;

    /**
     * Creates a new packet of SEND_PLAYER_TOOK_DAMAGE.
     * @param sessionId The player's session id.
     */
    public PacketSendPlayerTookDamage(int sessionId) {
        super(PacketOpcodes.SEND_PLAYER_TOOK_DAMAGE);
        this.playerSessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.playerSessionId);
    }
}
package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetPlayerDisconnected extends PSSendPacket {
    private final String playerName;

    /**
     * Creates a new packet of SEND_MODOPWET_PLAYER_DISCONNECTED.
     * @param playerName The player's name.
     */
    public PacketSendModopwetPlayerDisconnected(String playerName) {
        super(PacketOpcodes.SEND_MODOPWET_PLAYER_DISCONNECTED);
        this.playerName = playerName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.playerName, true);
    }
}
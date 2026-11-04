package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerWatch extends PSSendPacket {
    private final String playerName;

    /**
     * Creates a new packet of SEND_PLAYER_WATCH.
     * @param playerName The player's name.
     */
    public PacketSendPlayerWatch(String playerName) {
        super(PacketOpcodes.SEND_PLAYER_WATCH);
        this.playerName = playerName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.playerName, true);
    }
}
package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendBanConsideration extends PSSendPacket {
    private final String playerName;

    /**
     * Creates a new packet of SendBanConsideration.
     * @param playerName The player's name.
     */
    public PacketSendBanConsideration(String playerName) {
        super(LegacyPacketOpcodes.SendBanConsideration);
        this.playerName = playerName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.playerName, false);
    }
}
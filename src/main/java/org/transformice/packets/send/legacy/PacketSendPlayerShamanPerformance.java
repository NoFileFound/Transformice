package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerShamanPerformance extends PSSendPacket {
    private final String playerName;
    private final int numCompleted;

    /**
     * Creates a new packet of SendPlayerShamanPerformance.
     * @param playerName The player's name.
     * @param numCompleted The total mice entered in the hole in his turn as a shaman.
     */
    public PacketSendPlayerShamanPerformance(String playerName, int numCompleted) {
        super(LegacyPacketOpcodes.SendPlayerShamanPerformance);
        this.playerName = playerName;
        this.numCompleted = numCompleted;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.numCompleted), false);
        return packet;
    }
}
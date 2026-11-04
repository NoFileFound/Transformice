package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerDeath extends PSSendPacket {
    private final int sessionId;
    private final int playerScore;
    private final int deathType;

    /**
     * Creates a new packet of SendPlayerDeath.
     * @param sessionId The player's session id.
     * @param playerScore The player's score.
     */
    public PacketSendPlayerDeath(int sessionId, int playerScore, int deathType) {
        super(LegacyPacketOpcodes.SendPlayerDeath);
        this.sessionId = sessionId;
        this.playerScore = playerScore;
        this.deathType = deathType;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.valueOf(this.sessionId), false);
        packet.writeByte(1);
        packet.writeUTF("0", false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.playerScore), false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.deathType), false);
        return packet;
    }
}
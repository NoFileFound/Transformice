package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeJoin extends PSSendPacket {
    private final int teamId;
    private final int teamPosition;
    private final int sessionId;
    private final String playerName;
    private final String tribeName;

    /**
     * Creates a new packet of SEND_MULODROME_JOIN.
     * @param teamId The team's id.
     * @param teamPosition  The team's position.
     * @param sessionId The player's session id.
     * @param playerName The player's name.
     * @param tribeName The player's tribe name.
     */
    public PacketSendMulodromeJoin(int teamId, int teamPosition, int sessionId, String playerName, String tribeName) {
        super(PacketOpcodes.SEND_MULODROME_JOIN);
        this.teamId = teamId;
        this.teamPosition = teamPosition;
        this.sessionId = sessionId;
        this.playerName = playerName;
        this.tribeName = tribeName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.teamId);
        packet.writeByte(this.teamPosition);
        packet.writeInt(this.sessionId);
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.tribeName, true);
        return packet;
    }
}
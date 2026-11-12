package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendMapVotePopup extends PSSendPacket {
    private final String mapName;
    private final int mapYesVotes;
    private final int mapNoVotes;

    /**
     * Creates a new packet of SendMapVotePopup.
     * @param mapName The map's author.
     * @param mapYesVotes The map's positive votes.
     * @param mapNoVotes The map's negative votes.
     */
    public PacketSendMapVotePopup(String mapName, int mapYesVotes, int mapNoVotes) {
        super(LegacyPacketOpcodes.SendMapVotePopup);
        this.mapName = mapName;
        this.mapYesVotes = mapYesVotes;
        this.mapNoVotes = mapNoVotes;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.mapName, false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.mapYesVotes), false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.mapNoVotes), false);
        return packet;
    }
}
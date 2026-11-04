package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendTotem extends PSSendPacket {
    private final int sessionId;
    private final int posX;
    private final int posY;
    private final String totem;

    /**
     * Creates a new legacy packet of SendTotem.
     * @param sessionId The player's session id.
     * @param posX The X-pos.
     * @param posY The Y-pos.
     * @param totem The totem info.
     */
    public PacketSendTotem(int sessionId, int posX, int posY, String totem) {
        super(LegacyPacketOpcodes.SendTotem);
        this.sessionId = sessionId;
        this.posX = posX;
        this.posY = posY;
        this.totem = totem;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.valueOf(this.sessionId), false);
        packet.writeUTF("#", false);
        packet.writeUTF(String.valueOf(this.posX), false);
        packet.writeUTF("#", false);
        packet.writeUTF(String.valueOf(this.posY), false);
        packet.writeUTF("#", false);
        packet.writeUTF(this.totem, false);
        return packet;
    }
}
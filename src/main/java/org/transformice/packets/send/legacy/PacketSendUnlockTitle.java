package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendUnlockTitle extends PSSendPacket {
    private final int sessionId;
    private final int titleId;
    private final int titleStars;

    /**
     * Creates a new packet of SendPlayerUnlockTitle.
     * @param sessionId The player's session id.
     * @param titleId The title's id.
     * @param titleStars The title's stars.
     */
    public PacketSendUnlockTitle(int sessionId, int titleId, int titleStars) {
        super(LegacyPacketOpcodes.SendPlayerUnlockTitle);
        this.sessionId = sessionId;
        this.titleId = titleId;
        this.titleStars = titleStars;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.valueOf(this.sessionId), false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.titleId), false);
        packet.writeByte(1);
        packet.writeUTF(String.valueOf(this.titleStars), false);
        return packet;
    }
}
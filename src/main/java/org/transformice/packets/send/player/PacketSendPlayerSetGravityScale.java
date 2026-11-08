package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerSetGravityScale extends PSSendPacket {
    private final int sessionId;
    private final int x;
    private final int y;

    /**
     * Creates a new packet of SEND_PLAYER_SET_GRAVITY_SCALE.
     * @param sessionId The player's session id.
     * @param x The X position.
     * @param y The Y position.
     */
    public PacketSendPlayerSetGravityScale(int sessionId, int x, int y) {
        super(PacketOpcodes.SEND_PLAYER_SET_GRAVITY_SCALE);
        this.sessionId = sessionId;
        this.x = x;
        this.y = y;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.sessionId);
        packet.writeInt128(this.x);
        packet.writeInt128(this.y);
        return packet;
    }
}
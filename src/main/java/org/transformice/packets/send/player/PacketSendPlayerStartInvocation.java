package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerStartInvocation extends PSSendPacket {
    private final int sessionId;
    private final int objectCode;
    private final int posX;
    private final int posY;
    private final int angle;
    private final String rotation;
    private final boolean invocation;

    /**
     * Creates a new packet of SEND_PLAYER_START_INVOCATION.
     * @param sessionId The player's session id.
     * @param objectCode The object's id.
     * @param posX The position's X.
     * @param posY The position's Y.
     * @param angle The angle.
     * @param rotation The rotation.
     * @param invocation The invocation.
     */
    public PacketSendPlayerStartInvocation(int sessionId, int objectCode, int posX, int posY, int angle, String rotation, boolean invocation) {
        super(PacketOpcodes.SEND_PLAYER_START_INVOCATION);
        this.sessionId = sessionId;
        this.objectCode = objectCode;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.rotation = rotation;
        this.invocation = invocation;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.sessionId);
        packet.writeShort((short) this.objectCode);
        packet.writeShort((short) this.posX);
        packet.writeShort((short) this.posY);
        packet.writeShort((short) this.angle);
        packet.writeUTF(this.rotation, true);
        packet.writeBoolean(this.invocation);
        return packet;
    }
}
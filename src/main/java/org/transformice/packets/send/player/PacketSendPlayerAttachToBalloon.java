package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerAttachToBalloon extends PSSendPacket {
    private final int sessionId;
    private final int objId;
    private final int speed;

    /**
     * Creates a new packet of SEND_PLAYER_ATTACH_TO_BALLOON.
     * @param sessionId The player's session id.
     * @param objId The balloon's object id.
     * @param speed The balloon's speed.
     */
    public PacketSendPlayerAttachToBalloon(int sessionId, int objId, int speed) {
        super(PacketOpcodes.SEND_PLAYER_ATTACH_TO_BALLOON);
        this.sessionId = sessionId;
        this.objId = objId;
        this.speed = speed;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeInt(this.objId).writeInt(this.speed);
    }
}
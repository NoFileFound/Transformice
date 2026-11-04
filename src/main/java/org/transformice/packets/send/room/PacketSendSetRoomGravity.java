package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSetRoomGravity extends PSSendPacket {
    private final int milliseconds;
    private final int velocityX;
    private final int velocityY;

    /**
     * Creates a new packet of SEND_SET_ROOM_GRAVITY.
     * @param milliseconds The milliseconds of the gravity.
     * @param velocityX The velocity's X.
     * @param velocityY The velocity's Y.
     */
    public PacketSendSetRoomGravity(int milliseconds, int velocityX, int velocityY) {
        super(PacketOpcodes.SEND_SET_ROOM_GRAVITY);
        this.milliseconds = milliseconds;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.milliseconds).writeInt(this.velocityX * 1000).writeInt(this.velocityY * 1000);
    }
}
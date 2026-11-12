package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomRoundCountDown extends PSSendPacket {
    private final boolean showCountDown;

    /**
     * Creates a new packet of SEND_ROOM_ROUND_COUNTDOWN.
     * @param showCountDown True if it will show 3 seconds countdown or else False.
     */
    public PacketSendRoomRoundCountDown(boolean showCountDown) {
        super(PacketOpcodes.SEND_ROOM_ROUND_COUNTDOWN);
        this.showCountDown = showCountDown;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.showCountDown);
    }
}
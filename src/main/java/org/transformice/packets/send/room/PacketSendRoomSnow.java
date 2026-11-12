package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomSnow extends PSSendPacket {
    private final boolean isEnabled;
    private final int snowPower;

    /**
     * Creates a new packet of SEND_ROOM_SNOW.
     * @param isEnabled Is the snow enabled.
     * @param snowPower The snow power.
     */
    public PacketSendRoomSnow(boolean isEnabled, int snowPower) {
        super(PacketOpcodes.SEND_ROOM_SNOW);
        this.isEnabled = isEnabled;
        this.snowPower = snowPower;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isEnabled).writeShort((short) this.snowPower);
    }
}
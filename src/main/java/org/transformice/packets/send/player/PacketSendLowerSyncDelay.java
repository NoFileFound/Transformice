package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLowerSyncDelay extends PSSendPacket {
    private final int delay;

    /**
     * Creates a new send packet of SEND_LOWER_SYNC_DELAY.
     * @param delay The delay.
     */
    public PacketSendLowerSyncDelay(int delay) {
        super(PacketOpcodes.SEND_LOWER_SYNC_DELAY);
        this.delay = delay;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedShort(delay);
    }
}
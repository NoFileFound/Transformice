package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeLock extends PSSendPacket {
    private final int type;
    private final boolean isLocked;

    /**
     * Creates a new packet of SEND_TRADE_LOCK.
     * @[param type The agreement type.
     * @param isLocked is the trade locked.
     */
    public PacketSendTradeLock(int type, boolean isLocked) {
        super(PacketOpcodes.SEND_TRADE_LOCK);
        this.type = type;
        this.isLocked = isLocked;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.type).writeBoolean(this.isLocked);
    }
}
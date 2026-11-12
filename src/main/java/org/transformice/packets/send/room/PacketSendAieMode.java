package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendAieMode extends PSSendPacket {
    private final boolean enable;
    private final int sensibility;

    /**
     * Creates a new packet of SEND_AIE_MODE.
     * @param enable Is fall damage enabled.
     * @param sensibility The damage's sensibility.
     */
    public PacketSendAieMode(boolean enable, int sensibility) {
        super(PacketOpcodes.SEND_AIE_MODE);
        this.enable = enable;
        this.sensibility = sensibility;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.enable);
        packet.writeInt128(this.sensibility * 1000);
        return packet;
    }
}
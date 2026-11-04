package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendChangeTribeHouseResult extends PSSendPacket {
    private final int result;

    /**
     * Creates a new packet of ChangeTribeHouseResult.
     * @param errorCode The error's code.
     */
    public PacketSendChangeTribeHouseResult(int errorCode) {
        super(LegacyPacketOpcodes.SendTribeChangeHouseResult);
        this.result = errorCode;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.valueOf(this.result), false);
        return packet;
    }
}
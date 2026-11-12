package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerBanScreen extends PSSendPacket {
    private final String reason;
    private final long duration;

    /**
     * Creates a legacy packet of PacketSendPlayerBanScreen.
     * @param reason The ban reason.
     * @param duration The ban duration.
     */
    public PacketSendPlayerBanScreen(String reason, long duration) {
        super(LegacyPacketOpcodes.SendPlayerBanScreen);
        this.reason = reason;
        this.duration = (duration == -1 ? 0 : duration);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        if (this.duration > 0) {
            packet.writeUTF(String.valueOf(this.duration), false);
            packet.writeByte(1);
        }

        packet.writeUTF(this.reason, false);
        return packet;
    }
}
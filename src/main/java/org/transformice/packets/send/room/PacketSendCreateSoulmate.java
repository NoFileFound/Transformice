package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCreateSoulmate extends PSSendPacket {
    private final int playerSession1;
    private final int playerSession2;
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_CREATE_SOULMATE.
     * @param playerSession1 The first player's session id.
     * @param playerSession2 The second player's session id.
     * @param isEnabled Is the soulmate enabled.
     */
    public PacketSendCreateSoulmate(int playerSession1, int playerSession2, boolean isEnabled) {
        super(PacketOpcodes.SEND_CREATE_SOULMATE);
        this.playerSession1 = playerSession1;
        this.playerSession2 = playerSession2;
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.isEnabled);
        packet.writeInt(this.playerSession1);
        if (this.isEnabled)
            packet.writeInt(this.playerSession2);

        return packet;
    }
}
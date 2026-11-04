package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendCatchTheCheeseMap extends PSSendPacket {
    private final int shamanSessionId;

    /**
     * Creates a new packet of SendCatchTheCheeseMap.
     * @param shamanSessionId The shaman's session id.
     */
    public PacketSendCatchTheCheeseMap(int shamanSessionId) {
        super(LegacyPacketOpcodes.SendCatchTheCheeseMap);
        this.shamanSessionId = shamanSessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(String.valueOf(this.shamanSessionId), false);
    }
}
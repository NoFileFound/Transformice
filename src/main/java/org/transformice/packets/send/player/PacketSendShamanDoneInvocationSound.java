package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanDoneInvocationSound extends PSSendPacket {
    private final int shamanObjectId;

    /**
     * Creates a new packet of SEND_SHAMAN_DONE_INVOCATION_SOUND.
     * @param shamanObjectId The shaman's object id.
     */
    public PacketSendShamanDoneInvocationSound(int shamanObjectId) {
        super(PacketOpcodes.SEND_SHAMAN_DONE_INVOCATION_SOUND);
        this.shamanObjectId = shamanObjectId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.shamanObjectId);
    }
}
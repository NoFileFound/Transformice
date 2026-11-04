package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanResetSkills extends PSSendPacket {
    /**
     * Creates a new packet of SEND_SHAMAN_RESET_SKILLS.
     */
    public PacketSendShamanResetSkills() {
        super(PacketOpcodes.SEND_SHAMAN_RESET_SKILLS);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}
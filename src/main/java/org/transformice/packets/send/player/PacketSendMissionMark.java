package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMissionMark extends PSSendPacket {
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_MISSION_MARK.
     * @param isEnabled Is the mission mark enabled.
     */
    public PacketSendMissionMark(boolean isEnabled) {
        super(PacketOpcodes.SEND_MISSION_MARK);
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isEnabled);
    }
}
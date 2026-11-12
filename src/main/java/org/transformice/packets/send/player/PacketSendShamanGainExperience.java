package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanGainExperience extends PSSendPacket {
    private final int amount;

    /**
     * Creates a new packet of SEND_SHAMAN_GAIN_EXPERIENCE.
     * @param amount The experience that the shaman gained.
     */
    public PacketSendShamanGainExperience(int amount) {
        super(PacketOpcodes.SEND_SHAMAN_GAIN_EXPERIENCE);
        this.amount = amount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.amount);
    }
}
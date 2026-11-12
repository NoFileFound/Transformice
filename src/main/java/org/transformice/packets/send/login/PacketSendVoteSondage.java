package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendVoteSondage extends PSSendPacket {
    private final int optionId;

    /**
     * Creates a new packet of SEND_SONDAGE_VOTE.
     * @param optionId The option's id to vote.
     */
    public PacketSendVoteSondage(int optionId) {
        super(PacketOpcodes.SEND_SONDAGE_VOTE);
        this.optionId = optionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedByte(this.optionId);
    }
}
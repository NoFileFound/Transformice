package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerPet extends PSSendPacket {
    private final int sessionId;
    private final int petType;

    /**
     * Creates a new packet of SEND_PLAYER_PET.
     * @param sessionId The player's session id.
     * @param petType The player's pet type.
     */
    public PacketSendPlayerPet(int sessionId, int petType) {
        super(PacketOpcodes.SEND_PLAYER_PET);
        this.sessionId = sessionId;
        this.petType = petType;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeUnsignedByte(this.petType);
    }
}
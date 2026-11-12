package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEvolutionSkill extends PSSendPacket {
    private final int sessionId;
    private final int arg;

    /**
     * Creates a new packet of SEND_EVOLUTION_SKILL.
     * @param sessionId The player's session id.
     * @param arg The skill arg.
     */
    public PacketSendEvolutionSkill(int sessionId, int arg) {
        super(PacketOpcodes.SEND_EVOLUTION_SKILL);
        this.sessionId = sessionId;
        this.arg = arg;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeUnsignedByte(this.arg);
    }
}
package org.transformice.packets.send.inventory;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendUseConsumable extends PSSendPacket {
    private final int sessionId;
    private final int consumableId;

    /**
     * Creates a new packet of SEND_USE_CONSUMABLE.
     * @param sessionId The player's session id.
     * @param consumableId The consumable id.
     */
    public PacketSendUseConsumable(int sessionId, int consumableId) {
        super(PacketOpcodes.SEND_USE_CONSUMABLE);
        this.sessionId = sessionId;
        this.consumableId = consumableId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort((short) this.consumableId);
    }
}
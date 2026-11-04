package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerSynchronizer extends PSSendPacket {
    private final int sessionId;
    private final boolean spawn_initial_objects;

    /**
     * Creates a new packet of SendPlayerSynchronizer.
     * @param sessionId The synchronizer's session id.
     * @param spawn_initial_objects Should spawn the initial objects when a player enters a room.
     */
    public PacketSendPlayerSynchronizer(int sessionId, boolean spawn_initial_objects) {
        super(LegacyPacketOpcodes.SendPlayerSynchronizer);
        this.sessionId = sessionId;
        this.spawn_initial_objects = spawn_initial_objects;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.valueOf(this.sessionId), false);
        if (this.spawn_initial_objects) {
            packet.writeByte(1);
            packet.writeUTF("", false);
        }

        return packet;
    }
}
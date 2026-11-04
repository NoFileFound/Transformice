package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomEnter extends PSSendPacket {
    private final boolean isOfficial;
    private final String roomName;
    private final String roomCommunity;

    /**
     * Creates a new packet of SEND_ROOM_ENTER.
     * @param isOfficial Is the room official in the game.
     * @param roomName The room's name.
     * @param roomCommunity The room's community code.
     */
    public PacketSendRoomEnter(boolean isOfficial, String roomName, String roomCommunity) {
        super(PacketOpcodes.SEND_ROOM_ENTER);
        this.isOfficial = isOfficial;
        this.roomName = roomName;
        this.roomCommunity = roomCommunity;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.isOfficial);
        packet.writeUTF(this.roomName, true);
        packet.writeUTF(this.roomCommunity, true);
        return packet;
    }
}
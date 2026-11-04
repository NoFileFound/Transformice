package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetRoomPasswordProtected extends PSSendPacket {
    private final String playerName;
    private final String roomName;
    private final boolean isPasswordProtected;

    /**
     * Creates a new packet of SEND_MODOPWET_ROOM_PASSWORD_PROTECTED.
     * @param playerName The player's name.
     * @param roomName The player's room name.
     * @param isPasswordProtected is the player's room password protected.
     */
    public PacketSendModopwetRoomPasswordProtected(String playerName, String roomName, boolean isPasswordProtected) {
        super(PacketOpcodes.SEND_MODOPWET_ROOM_PASSWORD_PROTECTED);
        this.playerName = playerName;
        this.roomName = roomName;
        this.isPasswordProtected = isPasswordProtected;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.roomName, true);
        packet.writeBoolean(this.isPasswordProtected);
        return packet;
    }
}
package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerRaiseItem extends PSSendPacket {
    private final int id;
    private final int sessionId;
    private final int arg0;
    private final String arg1;

    /**
     * Creates a new packet of SEND_PLAYER_RAISE_ITEM.
     * @param sessionId The session's id.
     * @param id The item's id.
     * @param arg1 The argument 1.
     */
    public PacketSendPlayerRaiseItem(int sessionId, int id, int arg1) {
        super(PacketOpcodes.SEND_PLAYER_RAISE_ITEM);
        this.id = id;
        this.sessionId = sessionId;
        this.arg0 = arg1;
        this.arg1 = "";
    }

    /**
     * Creates a new packet of SEND_PLAYER_RAISE_ITEM.
     * @param sessionId The session's id.
     * @param id The raise's id.
     * @param arg1 The argument 1.
     */
    public PacketSendPlayerRaiseItem(int sessionId, int id, String arg1) {
        super(PacketOpcodes.SEND_PLAYER_RAISE_ITEM);
        this.id = id;
        this.sessionId = sessionId;
        this.arg0 = 0;
        this.arg1 = arg1;
    }

    /**
     * Creates a new packet of SEND_PLAYER_RAISE_ITEM.
     * @param sessionId The session's id.
     * @param id The raise's id.
     * @param arg1 The argument 1.
     * @param arg2 The argument 2.
     */
    public PacketSendPlayerRaiseItem(int sessionId, int id, String arg1, int arg2) {
        super(PacketOpcodes.SEND_PLAYER_RAISE_ITEM);
        this.id = id;
        this.sessionId = sessionId;
        this.arg0 = arg2;
        this.arg1 = arg1;
    }



    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.sessionId);
        packet.writeByte(this.id);
        if (this.id == 5) {
            packet.writeUTF(this.arg1, true);
        }
        else if (this.id == 7) {
            packet.writeUTF(this.arg1, true);
            packet.writeUnsignedByte(this.arg0);
        }
        else {
            packet.writeInt(this.arg0);
        }

        return packet;
    }
}
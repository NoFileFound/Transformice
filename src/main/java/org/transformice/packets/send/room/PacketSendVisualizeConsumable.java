package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendVisualizeConsumable extends PSSendPacket {
    private final int sessionId;
    private final int id;
    private final Object[] args;

    /**
     * Creates a new packet of SEND_VISUALIZE_CONSUMABLE.
     * @param sessionId The session's id.
     * @param id The consumable id.
     * @param args The arguments.
     */
    public PacketSendVisualizeConsumable(int sessionId, int id, Object[] args) {
        super(PacketOpcodes.SEND_VISUALIZE_CONSUMABLE);
        this.sessionId = sessionId;
        this.id = id;
        this.args = args;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedByte(this.id);
        switch (this.id) {
            case 1:
                packet.writeUnsignedShort((Integer)this.args[0]);
                packet.writeInt((Integer)this.args[1]);
                break;

            case 2:
                packet.writeInt(this.sessionId);
                packet.writeInt((Integer)this.args[0]);
                packet.writeInt((Integer)this.args[1]);
                packet.writeInt((Integer)this.args[2]);
                packet.writeInt((Integer)this.args[3]);
                packet.writeInt((Integer)this.args[4]);
                break;

            case 3:
                packet.writeUTF((String) this.args[0], true);
                break;

            case 4:
                packet.writeInt(this.sessionId);
                packet.writeInt((Integer)this.args[0]);
                break;

            case 5:
                packet.writeInt(this.sessionId);
                packet.writeUnsignedShort((Integer)this.args[0]);
                packet.writeByte((Integer)this.args[1]);
                break;
        }

        return packet;
    }
}
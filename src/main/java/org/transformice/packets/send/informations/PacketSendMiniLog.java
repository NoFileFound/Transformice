package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMiniLog extends PSSendPacket {
    private final int x;
    private final int y;
    private final String title;
    private final String message;

    /**
     * Creates a new packet of SEND_MINI_LOG.
     * @param x The X position.
     * @param y The Y position.
     * @param title The log's title.
     * @param message The log's message.
     */
    public PacketSendMiniLog(int x, int y, String title, String message) {
        super(PacketOpcodes.SEND_MINI_LOG);
        this.x = x;
        this.y = y;
        this.title = title;
        this.message = message;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeShort((short) (this.x * this.y));
        packet.writeUTF(this.title, true);
        packet.writeUTF(this.message, true);
        return packet;
    }
}
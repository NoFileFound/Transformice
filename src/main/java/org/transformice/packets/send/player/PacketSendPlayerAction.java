package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerAction extends PSSendPacket {
    private final int sessionId;
    private final int actionType;
    private final String flag;
    private final boolean isFromLua;

    /**
     * Creates a new packet of SEND_PLAYER_ACTION.
     * @param sessionId The player's session id.
     * @param actionType The action type.
     * @param flag The flag code if action is flag.
     * @param isFromLua Is the packet sent from lua.
     */
    public PacketSendPlayerAction(int sessionId, int actionType, String flag, boolean isFromLua) {
        super(PacketOpcodes.SEND_PLAYER_ACTION);
        this.sessionId = sessionId;
        this.actionType = actionType;
        this.flag = flag;
        this.isFromLua = isFromLua;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.sessionId);
        packet.writeByte(this.actionType);
        if(!this.flag.isEmpty())
            packet.writeUTF(this.flag, true);

        packet.writeBoolean(this.isFromLua);
        return packet;
    }
}
package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanEarnedLevel extends PSSendPacket {
    private final String playerName;
    private final int level;

    /**
     * Creates a new packet of SEND_SHAMAN_EARNED_LEVEL.
     * @param playerName The player's name.
     * @param level The player's shaman level.
     */
    public PacketSendShamanEarnedLevel(String playerName, int level) {
        super(PacketOpcodes.SEND_SHAMAN_EARNED_LEVEL);
        this.playerName = playerName;
        this.level = level;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeShort((short) this.level);
        return packet;
    }
}
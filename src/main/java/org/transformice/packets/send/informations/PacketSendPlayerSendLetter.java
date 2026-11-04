package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerSendLetter extends PSSendPacket {
    private final String playerName;
    private final String playerLook;
    private final byte letterType;
    private final byte[] content;

    /**
     * Creates a new packet of SEND_PLAYER_SEND_LETTER.
     * @param playerName The player's name.
     * @param playerLook The player's look.
     * @param letterType The letter's type.
     * @param content The letter.
     */
    public PacketSendPlayerSendLetter(String playerName, String playerLook, byte letterType, byte[] content) {
        super(PacketOpcodes.SEND_PLAYER_SEND_LETTER);
        this.playerName = playerName;
        this.playerLook = playerLook;
        this.letterType = letterType;
        this.content = content;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.playerLook, true);
        packet.writeByte(this.letterType);
        packet.writeBytes(this.content);
        return packet;
    }
}
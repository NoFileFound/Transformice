package org.transformice.packets.send;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRockPaperScissorsPacket extends PSSendPacket {
    private final int playerSession1;
    private final int num;
    private final int playerSession2;
    private final int num2;

    /**
     * Creates a new packet of SEND_ROCK_PAPER_SCISSORS_PACKET.
     * @param playerSession1 The first player's session.
     * @param num The first player's choice.
     * @param playerSession2 The second player's session.
     * @param num2 The second player's choice.
     */
    public PacketSendRockPaperScissorsPacket(int playerSession1, int num, int playerSession2, int num2) {
        super(PacketOpcodes.SEND_ROCK_PAPER_SCISSORS_PACKET);
        this.playerSession1 = playerSession1;
        this.num = num;
        this.playerSession2 = playerSession2;
        this.num2 = num2;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.playerSession1).writeByte(this.num).writeInt(this.playerSession2).writeByte(this.num2);
    }
}
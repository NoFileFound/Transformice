package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerMove extends PSSendPacket {
    private final int posX;
    private final int posY;
    private final boolean posRelease;
    private final int velocityX;
    private final int velocityY;
    private final boolean velRelease;

    /**
     * Creates a new packet of SEND_PLAYER_MOVE.
     * @param posX The position's X.
     * @param posY The position's Y.
     * @param posRelease Should release the player on the position.
     * @param velocityX The velocity's X.
     * @param velocityY The velocity's Y.
     * @param velRelease Should release the player on the velocity.
     */
    public PacketSendPlayerMove(int posX, int posY, boolean posRelease, int velocityX, int velocityY, boolean velRelease) {
        super(PacketOpcodes.SEND_PLAYER_MOVE);
        this.posX = posX;
        this.posY = posY;
        this.posRelease = posRelease;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velRelease = velRelease;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeBoolean(this.posRelease);
        packet.writeInt128(this.velocityX);
        packet.writeInt128(this.velocityY);
        packet.writeBoolean(this.velRelease);
        return packet;
    }
}
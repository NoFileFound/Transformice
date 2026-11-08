package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendExplosion extends PSSendPacket {
    private final int posX;
    private final int posY;
    private final int power;
    private final int distance;
    private final boolean miceOnly;

    /**
     * Creates a new packet of SEND_EXPLOSION.
     * @param posX The X position.
     * @param posY The Y position.
     * @param power The explosion power.
     * @param distance The explosion distance.
     * @param miceOnly Is mice only.
     */
    public PacketSendExplosion(int posX, int posY, int power, int distance, boolean miceOnly) {
        super(PacketOpcodes.SEND_EXPLOSION);
        this.posX = posX;
        this.posY = posY;
        this.power = power;
        this.distance = distance;
        this.miceOnly = miceOnly;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeInt128(this.power);
        packet.writeInt128(this.distance);
        packet.writeBoolean(this.miceOnly);
        return packet;
    }
}
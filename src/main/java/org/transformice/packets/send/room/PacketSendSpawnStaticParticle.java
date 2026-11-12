package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSpawnStaticParticle extends PSSendPacket {
    private final int particleType;
    private final int posX;
    private final int posY;

    /**
     * Creates a new packet of SEND_SPAWN_STATIC_PARTICLE.
     * @param particleType The particle's type.
     * @param posX The particle's position on X-axis.
     * @param posY The particle's position on Y-axis.
     */
    public PacketSendSpawnStaticParticle(int particleType, int posX, int posY) {
        super(PacketOpcodes.SEND_SPAWN_STATIC_PARTICLE);
        this.particleType = particleType;
        this.posX = posX;
        this.posY = posY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.particleType).writeShort((short) this.posX).writeShort((short) this.posY);
    }
}
package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaDisplayParticle extends PSSendPacket {
    private final int id;
    private final int x;
    private final int y;
    private final int velX;
    private final int velY;
    private final int accX;
    private final int accY;

    /**
     * Creates a new packet of SEND_LUA_DISPLAY_PARTICLE.
     * @param id The particle's id.
     * @param x The X position.
     * @param y The Y position.
     * @param velX The X velocity.
     * @param velY The Y velocity.
     * @param accX The X acceleration.
     * @param accY The Y acceleration.
     */
    public PacketSendLuaDisplayParticle(int id, int x, int y, int velX, int velY, int accX, int accY) {
        super(PacketOpcodes.SEND_LUA_DISPLAY_PARTICLE);
        this.id = id;
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.accX = accX;
        this.accY = accY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.id);
        packet.writeInt128(this.x);
        packet.writeInt128(this.y);
        packet.writeInt128(this.velX * 100);
        packet.writeInt128(this.velY * 100);
        packet.writeInt128(this.accX * 100);
        packet.writeInt128(this.accY * 100);
        return packet;
    }
}
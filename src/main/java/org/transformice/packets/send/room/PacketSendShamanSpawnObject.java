package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanSpawnObject extends PSSendPacket {
    private final int objectId;
    private final int shamanObjectId;
    private final int posX;
    private final int posY;
    private final int angle;
    private final int velX;
    private final int velY;
    private final boolean has_contact_listener;
    private final boolean is_collidable;
    private final byte[] colors;

    /**
     * Creates a new packet of SEND_SHAMAN_SPAWN_OBJECT.
     * @param objectId The object's id.
     * @param shamanObjectId The shaman's object id.
     * @param posX The position's X.
     * @param posY The position's Y.
     * @param angle The angle.
     * @param velX The velocity's X.
     * @param velY The velocity's Y.
     * @param has_contact_listener Has the object contact listener.
     * @param is_collidable Is collidable with the other players.
     * @param colors The shaman's object colors.
     */
    public PacketSendShamanSpawnObject(int objectId, int shamanObjectId, int posX, int posY, int angle, int velX, int velY, boolean has_contact_listener, boolean is_collidable, byte[] colors) {
        super(PacketOpcodes.SEND_SHAMAN_SPAWN_OBJECT);
        this.objectId = objectId;
        this.shamanObjectId = shamanObjectId;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.velX = velX;
        this.velY = velY;
        this.has_contact_listener = has_contact_listener;
        this.is_collidable = is_collidable;
        this.colors = colors;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.objectId);
        packet.writeInt128(this.shamanObjectId);
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeInt128(this.angle * 100);
        packet.writeInt128(this.velX * 100);
        packet.writeInt128(this.velY * 100);
        packet.writeBoolean(this.has_contact_listener);
        packet.writeBoolean(this.is_collidable);
        packet.writeByte(this.colors.length);
        for (int color : this.colors)
            packet.writeInt(color);

        return packet;
    }
}
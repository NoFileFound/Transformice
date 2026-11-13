package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaAddPhysicObject extends PSSendPacket {
    private final int id;
    private final boolean dynamic;
    private final int groundId;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final boolean foreground;
    private final int friction;
    private final int restitution;
    private final int angle;
    private final int color;
    private final boolean miceCollidable;
    private final boolean groundCollidable;
    private final boolean fixedRotation;
    private final int mass;
    private final int linearDamping;
    private final int angularDamping;
    private final boolean invisible;
    private final String imageDescription;
    private final boolean hasContactListener;

    /**
     * Creates a new packet of SEND_LUA_ADD_PHYSIC_OBJECT.
     * @param id The object's id.
     * @param dynamic Is the object dynamic.
     * @param groundId The object's ground id.
     * @param x The X.
     * @param y The Y.
     * @param width The width.
     * @param height The height.
     * @param foreground The foreground.
     * @param friction The friction.
     * @param restitution The restitution.
     * @param angle The angle.
     * @param color The color.
     * @param miceCollidable Is mice collidable.
     * @param groundCollidable Is ground collidable.
     * @param fixedRotation The fixed rotation.
     * @param mass The mass.
     * @param linearDamping The linear damping.
     * @param angularDamping The angular damping.
     * @param invisible Is invisible.
     * @param imageDescription The image description.
     * @param hasContactListener Has contact listener.
     */
    public PacketSendLuaAddPhysicObject(int id, boolean dynamic, int groundId, int x, int y, int width, int height, boolean foreground, int friction, int restitution, int angle, int color, boolean miceCollidable, boolean groundCollidable, boolean fixedRotation, int mass, int linearDamping, int angularDamping, boolean invisible, String imageDescription, boolean hasContactListener) {
        super(PacketOpcodes.SEND_LUA_ADD_PHYSIC_OBJECT);
        this.id = id;
        this.dynamic = dynamic;
        this.groundId = groundId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.foreground = foreground;
        this.friction = friction;
        this.restitution = restitution;
        this.angle = angle;
        this.color = color;
        this.miceCollidable = miceCollidable;
        this.groundCollidable = groundCollidable;
        this.fixedRotation = fixedRotation;
        this.mass = mass;
        this.linearDamping = linearDamping;
        this.angularDamping = angularDamping;
        this.invisible = invisible;
        this.imageDescription = imageDescription;
        this.hasContactListener = hasContactListener;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.id);
        packet.writeBoolean(this.dynamic);
        packet.writeByte(this.groundId);
        packet.writeInt128(this.x);
        packet.writeInt128(this.y);
        packet.writeInt128(this.width);
        packet.writeInt128(this.height);
        packet.writeBoolean(this.foreground);
        packet.writeInt128(this.friction);
        packet.writeInt128(this.restitution);
        packet.writeInt128(this.angle);
        packet.writeBoolean(this.color != -1);
        packet.writeInt(this.color);
        packet.writeBoolean(this.miceCollidable);
        packet.writeBoolean(this.groundCollidable);
        packet.writeBoolean(this.fixedRotation);
        packet.writeInt128(this.mass);
        packet.writeInt128(this.linearDamping);
        packet.writeInt128(this.angularDamping);
        packet.writeBoolean(this.invisible);
        packet.writeUTF(this.imageDescription, true);
        packet.writeBoolean(this.hasContactListener);
        return packet;
    }
}
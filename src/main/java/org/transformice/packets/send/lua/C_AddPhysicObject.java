package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddPhysicObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddPhysicObject(int id, boolean dynamic, int groundId, int x, int y, int width, int height, boolean foreground, int friction, int restitution, int angle, boolean mice_collidable, boolean ground_collidable, boolean fixed_rotation, int mass, int linear_damping, int angular_damping, boolean invisible, String image_description, boolean has_contact_listener) {
        this.byteArray.writeInt128(id);
        this.byteArray.writeBoolean(dynamic);
        this.byteArray.writeByte(groundId);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(width);
        this.byteArray.writeInt128(height);
        this.byteArray.writeBoolean(foreground);
        this.byteArray.writeInt128(friction);
        this.byteArray.writeInt128(restitution);
        this.byteArray.writeInt128(angle);
        this.byteArray.writeBoolean(false);
        this.byteArray.writeInt(-1);
        this.byteArray.writeBoolean(mice_collidable);
        this.byteArray.writeBoolean(ground_collidable);
        this.byteArray.writeBoolean(fixed_rotation);
        this.byteArray.writeInt128(mass);
        this.byteArray.writeInt128(linear_damping);
        this.byteArray.writeInt128(angular_damping);
        this.byteArray.writeBoolean(invisible);
        this.byteArray.writeString(image_description);
        this.byteArray.writeBoolean(has_contact_listener);
    }

    public C_AddPhysicObject(int id, boolean dynamic, int groundId, int x, int y, int width, int height, boolean foreground, int friction, int restitution, int angle, int color, boolean mice_collidable, boolean ground_collidable, boolean fixed_rotation, int mass, int linear_damping, int angular_damping, boolean invisible, String image_description, boolean has_contact_listener) {
        this.byteArray.writeInt128(id);
        this.byteArray.writeBoolean(dynamic);
        this.byteArray.writeByte(groundId);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(width);
        this.byteArray.writeInt128(height);
        this.byteArray.writeBoolean(foreground);
        this.byteArray.writeInt128(friction);
        this.byteArray.writeInt128(restitution);
        this.byteArray.writeInt128(angle);
        this.byteArray.writeBoolean(true);
        this.byteArray.writeInt(color);
        this.byteArray.writeBoolean(mice_collidable);
        this.byteArray.writeBoolean(ground_collidable);
        this.byteArray.writeBoolean(fixed_rotation);
        this.byteArray.writeInt128(mass);
        this.byteArray.writeInt128(linear_damping);
        this.byteArray.writeInt128(angular_damping);
        this.byteArray.writeBoolean(invisible);
        this.byteArray.writeString(image_description);
        this.byteArray.writeBoolean(has_contact_listener);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 28;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
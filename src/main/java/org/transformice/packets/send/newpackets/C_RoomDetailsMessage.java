package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Room;
import org.transformice.packets.SendPacket;

public final class C_RoomDetailsMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomDetailsMessage(Room.RoomDetails roomDetails) {
        this.byteArray.writeBoolean(roomDetails.withoutShamanSkills);
        this.byteArray.writeBoolean(roomDetails.withoutPhysicalConsumables);
        this.byteArray.writeBoolean(roomDetails.withoutAdventureMaps);
        this.byteArray.writeBoolean(roomDetails.withMiceCollisions);
        this.byteArray.writeBoolean(roomDetails.withFallDamage);
        this.byteArray.writeUnsignedByte(roomDetails.roundDuration);
        this.byteArray.writeInt(roomDetails.miceWeight);
        this.byteArray.writeShort(roomDetails.maximumPlayers);
        this.byteArray.writeUnsignedByte(roomDetails.mapRotation.size());
        for (int i = roomDetails.mapRotation.size() - 1; i >= 0; i--) {
            this.byteArray.writeUnsignedByte(roomDetails.mapRotation.get(i));
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
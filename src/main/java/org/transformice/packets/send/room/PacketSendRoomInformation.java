package org.transformice.packets.send.room;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomInformation extends PSSendPacket {
    private final boolean disabledShamanSkills;
    private final boolean disabledPhysicalConsumables;
    private final boolean disabledAdventureMaps;
    private final boolean enabledMiceCollisions;
    private final boolean enabledFallDamage;
    private final int roundDuration;
    private final int miceWeight;
    private final int maximumPlayers;
    private final List<Integer> mapRotation;

    /**
     * Creates a new packet of SEND_ROOM_INFORMATION.
     * @param disabledShamanSkills Are the skills disabled in the room.
     * @param disabledPhysicalConsumables Are the physical consumables disabled in the room.
     * @param disabledAdventureMaps Are the adventure maps disabled in the room.
     * @param enabledMiceCollisions Are the mice collisions enabled in the room.
     * @param enabledFallDamage Is the fall damage enabled in the room.
     * @param roundDuration The round duration in percentage.
     * @param miceWeight The mice weight.
     * @param maximumPlayers The maximum players the room can hold.
     * @param mapRotation The map rotation.
     */
    public PacketSendRoomInformation(boolean disabledShamanSkills, boolean disabledPhysicalConsumables, boolean disabledAdventureMaps, boolean enabledMiceCollisions, boolean enabledFallDamage, int roundDuration, int miceWeight, int maximumPlayers, List<Integer> mapRotation) {
        super(PacketOpcodes.SEND_ROOM_INFORMATION);
        this.disabledShamanSkills = disabledShamanSkills;
        this.disabledPhysicalConsumables = disabledPhysicalConsumables;
        this.disabledAdventureMaps = disabledAdventureMaps;
        this.enabledMiceCollisions = enabledMiceCollisions;
        this.enabledFallDamage = enabledFallDamage;
        this.roundDuration = roundDuration;
        this.miceWeight = miceWeight;
        this.maximumPlayers = maximumPlayers;
        this.mapRotation = mapRotation;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        PSByteArray packet = new PSByteArray();
        packet.writeBoolean(this.disabledShamanSkills);
        packet.writeBoolean(this.disabledPhysicalConsumables);
        packet.writeBoolean(this.disabledAdventureMaps);
        packet.writeBoolean(this.enabledMiceCollisions);
        packet.writeBoolean(this.enabledFallDamage);
        packet.writeUnsignedByte(this.roundDuration);
        packet.writeInt(this.miceWeight);
        packet.writeShort((short) this.maximumPlayers);
        packet.writeUnsignedByte(this.mapRotation.size());
        for (int rotation : this.mapRotation)
            packet.writeUnsignedByte(rotation);

        return packet;
    }
}
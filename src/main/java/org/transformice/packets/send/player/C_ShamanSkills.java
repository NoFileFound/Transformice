package org.transformice.packets.send.player;

// Imports
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanSkills implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanSkills(Map<Integer, Integer> skills, boolean refresh) {
        this.byteArray.writeByte(skills.size());
        for(var skill : skills.entrySet()) {
            this.byteArray.writeByte(skill.getKey());
            this.byteArray.writeByte(skill.getValue());
        }
        this.byteArray.writeBoolean(refresh);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
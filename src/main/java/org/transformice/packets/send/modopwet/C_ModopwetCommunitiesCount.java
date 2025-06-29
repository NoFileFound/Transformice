package org.transformice.packets.send.modopwet;

// Imports
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetCommunitiesCount implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetCommunitiesCount(Map<String, Integer> modoCommunities) {
        this.byteArray.writeUnsignedByte(modoCommunities.size());
        for (Map.Entry<String, Integer> entry : modoCommunities.entrySet()) {
            this.byteArray.writeString(entry.getKey());
            this.byteArray.writeUnsignedByte(entry.getValue());
        }
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
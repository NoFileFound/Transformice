package org.transformice.packets.send.legacy.room;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddAnchor implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddAnchor(String anchor) {
        this.byteArray.writeString(anchor, false);
    }

    public C_AddAnchor(List<String> anchors) {
        for(int i = 0; i < anchors.size() - 1; ++i) {
            this.byteArray.writeString(anchors.get(i), false);
            this.byteArray.writeByte(1);
        }

        if(anchors.size() > 0) {
            this.byteArray.writeString(anchors.getLast(), false);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
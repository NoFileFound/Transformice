package org.transformice.packets.send.newpackets;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LoadShamanSprites implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoadShamanSprites(List<Integer> shamanItems) {
        this.byteArray.writeInt128(shamanItems.size());
        for(Integer item : shamanItems) {
            this.byteArray.writeInt128(item);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 27;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
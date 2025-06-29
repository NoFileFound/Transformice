package org.transformice.packets.send.newpackets;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LoadItemSprites implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoadItemSprites(List<Integer> furs) {
        this.byteArray.writeInt128(furs.size());
        for(Integer fur : furs) {
            this.byteArray.writeInt128(fur);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 34;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
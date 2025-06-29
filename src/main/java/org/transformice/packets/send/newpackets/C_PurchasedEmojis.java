package org.transformice.packets.send.newpackets;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PurchasedEmojis implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PurchasedEmojis(List<Integer> emojis) {
        this.byteArray.writeInt128(emojis.size());
        for(Integer emoji : emojis) {
            this.byteArray.writeInt128(emoji);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.modopwet;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetAddLangues implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetAddLangues(List<String> langues) {
        this.byteArray.writeUnsignedShort(langues.size());
        for(String lang : langues) {
            this.byteArray.writeString(lang);
        }
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_InitMapEditor implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_InitMapEditor(boolean isForFirstTime, boolean isLeaving) {
        if(isForFirstTime) {

        }

        if(isLeaving) {
            this.byteArray.writeString("0", false);
        }
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
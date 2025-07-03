package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_InitMapEditor implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_InitMapEditor(int actionType) {
        if(actionType == 0) {
            // exit the map editor
            this.byteArray.writeString("0", false);
        } else if(actionType == 1) {
            // return to editor
            this.byteArray.writeString("", false);
            this.byteArray.writeByte(1);
            this.byteArray.writeString("", false);
        } else if(actionType == 2) {
            // validate the map
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
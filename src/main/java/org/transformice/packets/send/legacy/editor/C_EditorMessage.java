package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EditorMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EditorMessage(int actionType) {
        if(actionType == 0) {
            this.byteArray.writeString("", false);
        } else {
            this.byteArray.writeString("", false);
            this.byteArray.writeByte(1);
            this.byteArray.writeString("", false);
        }
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
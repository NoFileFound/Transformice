package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_QuestionPopup implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_QuestionPopup(String question, int badgeId, boolean isShamanItem, boolean arg1, String arg2) {
        this.byteArray.writeByte(1);
        this.byteArray.writeString(arg2);
        this.byteArray.writeUnsignedInt(badgeId);
        this.byteArray.writeBoolean(isShamanItem);
        this.byteArray.writeBoolean(arg1);
        this.byteArray.writeString(question);
    }

    public C_QuestionPopup(String question, int itemId, int itemCategory, int arg1, String arg2) {
        this.byteArray.writeByte(2);
        this.byteArray.writeUnsignedInt(arg1);
        this.byteArray.writeByte(itemCategory);
        this.byteArray.writeUnsignedInt(itemId);
        this.byteArray.writeString(arg2);
        this.byteArray.writeString(question);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
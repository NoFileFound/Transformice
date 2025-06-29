package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CreateNewNPC implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CreateNewNPC(int sessionId, String npcName, int npcTitle, boolean isFeminine, String npcLook, int x, int y, int npcEmote, boolean isFacingRight, boolean isFacePlayer, int npcInterface, String npcMessage) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeString(npcName);
        this.byteArray.writeShort((short)npcTitle);
        this.byteArray.writeBoolean(isFeminine);
        this.byteArray.writeString(npcLook);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(npcEmote);
        this.byteArray.writeBoolean(isFacingRight);
        this.byteArray.writeBoolean(isFacePlayer);
        this.byteArray.writeByte(npcInterface);
        this.byteArray.writeString(npcMessage);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_NPCPlayEmote implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_NPCPlayEmote(String npcName, int emote, int emoji_id) {
        this.byteArray.writeString(npcName);
        this.byteArray.writeShort((short)emote);
        this.byteArray.writeShort((short)emoji_id);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 23;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
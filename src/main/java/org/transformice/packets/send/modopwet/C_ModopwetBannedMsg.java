package org.transformice.packets.send.modopwet;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetBannedMsg implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetBannedMsg(String playerName, String bannedBy, int banHours, String banReason) {
        boolean useTranslation = banReason.equals("$MessageTriche") || banReason.equals("$MessageTricheDef");

        this.byteArray.writeString(playerName);
        this.byteArray.writeBoolean(useTranslation);
        this.byteArray.writeString(bannedBy);
        this.byteArray.writeInt(banHours);
        this.byteArray.writeString(banReason);
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
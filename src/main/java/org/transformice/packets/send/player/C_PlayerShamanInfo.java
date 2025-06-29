package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.SendPacket;

public final class C_PlayerShamanInfo implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerShamanInfo(Client firstShaman, Client secondShaman) {
        if (firstShaman == null) {
            // no shaman maps.
            this.byteArray.writeInt(0).writeInt(0).writeByte(0).writeByte(0).writeUnsignedShort(0).writeUnsignedShort(0).writeShort((short)0).writeShort((short)0).writeBoolean(false).writeBoolean(false);
            return;
        }

        this.byteArray.writeInt(firstShaman.getSessionId());
        this.byteArray.writeInt((secondShaman != null) ? secondShaman.getSessionId() : 0);

        this.byteArray.writeByte(firstShaman.getAccount().getShamanType());
        this.byteArray.writeByte((secondShaman != null) ? secondShaman.getAccount().getShamanType() : 0);

        this.byteArray.writeUnsignedShort((firstShaman.getAccount().isShamanNoSkills() ? 0 : firstShaman.getAccount().getPlayerSkills().size()));
        this.byteArray.writeUnsignedShort((secondShaman != null && secondShaman.getAccount().isShamanNoSkills()) ? secondShaman.getAccount().getPlayerSkills().size() : 0);
        this.byteArray.writeShort((short) (firstShaman.getParseSkillsInstance().getShamanBadge()));
        this.byteArray.writeShort((short) (secondShaman != null ? secondShaman.getParseSkillsInstance().getShamanBadge() : 0));

        this.byteArray.writeBoolean(firstShaman.getAccount().isShamanNoSkills());
        this.byteArray.writeBoolean((secondShaman != null && (secondShaman.getAccount().isShamanNoSkills())));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PunishmentTax implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PunishmentTax(int fraises) {
        this.byteArray.writeShort((short)fraises);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 41;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
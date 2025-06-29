package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_WriteChatCommand implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_WriteChatCommand(String command) {
        this.byteArray.writeString(command);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 48;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
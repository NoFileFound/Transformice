package org.transformice.packets.send.chat;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ServerMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ServerMessage(boolean isTab, String message) {
        this.byteArray.writeBoolean(isTab);
        this.byteArray.writeString(message);
        this.byteArray.writeByte(0);
    }

    public C_ServerMessage(boolean isTab, String message, List<String> args) {
        this.byteArray.writeBoolean(isTab);
        this.byteArray.writeString(message);
        this.byteArray.writeUnsignedByte(args.size());
        for(String arg : args) {
            this.byteArray.writeString(arg);
        }
    }

    @Override
    public int getC() {
        return 6;
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
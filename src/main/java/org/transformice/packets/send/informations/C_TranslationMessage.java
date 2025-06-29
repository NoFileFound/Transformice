package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TranslationMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TranslationMessage(String community, String message, String[] args) {
        this.byteArray.writeString(community);
        this.byteArray.writeString(message);
        this.byteArray.writeUnsignedByte(args.length);
        for(String arg : args) {
            this.byteArray.writeString(arg);
        }
    }

    public C_TranslationMessage(String community, String message) {
        this.byteArray.writeString(community);
        this.byteArray.writeString(message);
        this.byteArray.writeUnsignedByte(0);
    }

    @Override
    public int getC() {
        return 28;
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
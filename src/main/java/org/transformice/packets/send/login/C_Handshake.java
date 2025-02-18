package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_Handshake implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Handshake(int onlinePlayers, String language, String country) {
        this.byteArray.writeInt(onlinePlayers);
        this.byteArray.writeString(language);
        this.byteArray.writeString(country);
        this.byteArray.writeInt(Application.getSwfInfo().authorization_key);
        this.byteArray.writeBoolean(Application.getPropertiesInfo().twitchStreaming);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
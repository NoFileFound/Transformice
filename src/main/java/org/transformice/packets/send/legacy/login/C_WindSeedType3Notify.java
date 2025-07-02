package org.transformice.packets.send.legacy.login;

// Imports
import java.util.Base64;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

/**
 * 💀💀💀💀💀💀💀💀💀💀💀💀💀💀
 */
public final class C_WindSeedType3Notify implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_WindSeedType3Notify(String data) {
        this.byteArray.writeBytes(Base64.getDecoder().decode(data));
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
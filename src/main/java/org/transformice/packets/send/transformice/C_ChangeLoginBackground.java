package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_ChangeLoginBackground implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeLoginBackground() {
        this.byteArray.writeString(Application.getPropertiesInfo().event.banner_bg_img_legacy + "#" + Application.getPropertiesInfo().event.banner_fg_img_legacy + "#0#0");
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 99;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
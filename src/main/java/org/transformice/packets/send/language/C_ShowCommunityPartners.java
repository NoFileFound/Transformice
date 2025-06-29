package org.transformice.packets.send.language;

// Imports
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_ShowCommunityPartners implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShowCommunityPartners() {
        List<Map<String, String>> partners = Application.getPartnersInfo();

        this.byteArray.writeUnsignedShort(partners.size());
        for (Map<String, String> partner : partners) {
            this.byteArray.writeString(partner.get("name"));
            this.byteArray.writeString(partner.get("icon"));
        }
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
package org.transformice.packets.recv.language;

// Imports
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.language.C_OpenLink;

@SuppressWarnings("unused")
public final class S_OpenCommunityPartner implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        List<Map<String, String>> partners = Application.getPartnersInfo();
        String partnerName = data.readString();
        for(Map<String, String> partner : partners) {
            if(partner.get("name").equals(partnerName)) {
                client.sendPacket(new C_OpenLink(partner.get("url")));
            }
        }
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
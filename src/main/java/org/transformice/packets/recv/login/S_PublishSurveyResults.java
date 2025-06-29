package org.transformice.packets.recv.login;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_CreateSurvey;

@SuppressWarnings("unused")
public final class S_PublishSurveyResults implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.hasStaffPermission("Admin", "Survey")) return;

        String title = data.readString();
        List<String> answers = new ArrayList<>();
        while (data.getLength() > 0) {
            answers.add(data.readString());
        }

        for (Client player : client.getServer().getPlayers().values()) {
            player.sendPacket(new C_CreateSurvey(0, title, answers));
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
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
public final class S_CreateSurvey implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.hasStaffPermission("Admin", "Survey")) return;

        String title = "[" + client.getPlayerName() + "] " + data.readString();
        List<String> answers = new ArrayList<>();
        while (data.getLength() > 0) {
            answers.add(data.readString());
        }

        if (answers.size() > 1) {
            for (Client player : client.getServer().getPlayers().values()) {
				if(!player.isGuest())
					player.sendPacket(new C_CreateSurvey(client.getAccount().getId(), title, answers));
            }
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
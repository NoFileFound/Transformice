package org.transformice.packets.recv.cafe;

// Imports
import java.util.concurrent.TimeUnit;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@SuppressWarnings("unused")
public final class S_CreateNewPost implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.getServer().createCafePostTimer.get(client.getPlayerName()).getRemainingTime() <= 0) {
            client.getParseCafeInstance().sendNewTopicPost(data.readInt(), data.readString());
            client.getServer().createCafePostTimer.get(client.getPlayerName()).schedule(() -> {}, TimeUnit.SECONDS);
        } else {
            client.sendPacket(new C_TranslationMessage("", "$AttendreNouveauMessage", new String[]{String.valueOf(client.getServer().createCafePostTimer.get(client.getPlayerName()).getRemainingTime() / 1000)}));
        }
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 43;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
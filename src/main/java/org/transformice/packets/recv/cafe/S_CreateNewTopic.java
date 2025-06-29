package org.transformice.packets.recv.cafe;

// Imports
import java.util.concurrent.TimeUnit;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@SuppressWarnings("unused")
public final class S_CreateNewTopic implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.getServer().createCafeTopicTimer.get(client.getPlayerName()).getRemainingTime() <= 0) {
            client.getParseCafeInstance().sendNewTopic(data.readString(), data.readString());
            client.getServer().createCafeTopicTimer.get(client.getPlayerName()).schedule(() -> {}, TimeUnit.MINUTES);
        } else {
            client.sendPacket(new C_TranslationMessage("", "$AttendreNouveauSujet", new String[]{String.valueOf(client.getServer().createCafeTopicTimer.get(client.getPlayerName()).getRemainingTime() / 60000)}));
        }
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
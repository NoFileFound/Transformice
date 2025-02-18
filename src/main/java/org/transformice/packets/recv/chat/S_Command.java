package org.transformice.packets.recv.chat;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_Command implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data.decryptPacket(Application.getSwfInfo().packet_keys, fingerPrint);

        client.getServer().getCommandHandler().invokeCommand(client, data.readString(), false);
    }

    @Override
    public int getC() {
        return 6;
    }

    @Override
    public int getCC() {
        return 26;
    }
}
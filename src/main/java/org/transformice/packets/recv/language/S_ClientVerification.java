package org.transformice.packets.recv.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ClientVerification implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data = data.decryptIdentification(Application.getSwfInfo().packet_keys, String.valueOf(client.verCode));

        long verCode = data.readInt();
        if(verCode != client.verCode) {
            Application.getLogger().info(String.format("[Connection] The given IP Address %s failed to pass the protection. (Auth key -> %d | Expected -> %d)", client.getIpAddress(), verCode, client.verCode));
            client.closeConnection();
            return;
        }

        if(!data.readString().equals("-b7") || !data.readString().equals("--")) {
            client.closeConnection();
            return;
        }

        verCode = data.readInt();
        if(verCode != client.verCode) {
            Application.getLogger().info(String.format("[Connection] The given IP Address %s failed to pass the protection. (Auth key -> %d | Expected -> %d)", client.getIpAddress(), verCode, client.verCode));
            client.closeConnection();
            return;
        }

        if(!data.readString().equals("-``--,,") || data.readShort() != -701 || !data.readString().equals("--///v/")) {
            client.closeConnection();
            return;
        }

        data.readShort(); // 7
        data.readInt(); // 820
        if((data.readInt() ^ 801) != 8364) {
            client.closeConnection();
            return;
        }

        data.readInt(); // 8009
        data.readByte(); // 23 (0x17)
        data.readShort(); // 1826
        data.readShort(); // ???
        if(!data.readString().equals("+++")) {
            client.closeConnection();
            return;
        }

        client.verCode = -1;
        Application.getLogger().debug(String.format("[Connection] The given IP address %s successfully passed the verification", client.getIpAddress()));
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 47;
    }
}
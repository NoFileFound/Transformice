package org.transformice.packets.recv.language;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CLIENT_VERIFICATION)
public final class PacketRecvClientVerification extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int verCode = client.getVerificationCode();
        psData = psData.decryptIdentification(gameServer.getConfig().getSWFConfig().getPacketKeys(), String.valueOf(verCode));

        long revCode = psData.readInt();
        if(revCode != verCode)
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the authorization key of the client and the server. [%s != %s]", verCode, revCode));

        String serverString = psData.readUTF();
        if(!serverString.equals("-b7"))
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the server string of the client and the server. [%s != %s]", serverString, "-b7"));

        serverString = psData.readUTF();
        if(!serverString.equals("--"))
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the server string of the client and the server. [%s != %s]", serverString, "--"));

        revCode = psData.readInt();
        if(revCode != verCode)
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the authorization key of the client and the server #2. [%s != %s]", verCode, revCode));

        serverString = psData.readUTF();
        if(!serverString.equals("-``--,,"))
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the server string of the client and the server. [%s != %s]", serverString, "-``--,,"));

        psData.readShort(); // -701
        serverString = psData.readUTF();
        if(!serverString.equals("--///v/"))
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the server string of the client and the server. [%s != %s]", serverString, "--///v/"));

        psData.readShort(); // 7
        psData.readInt(); // 820
        psData.readInt(); // (psData.readInt() ^ 801) != 8364
        psData.readInt(); // 8009
        psData.readByte(); // 23 (0x17)
        psData.readShort(); // 1826
        psData.readShort(); // ???
        serverString = psData.readUTF();
        if(!serverString.equals("+++"))
            throw new PSReceivePacketException(String.format("[PacketRecvClientVerification] There is a mismatch between the server string of the client and the server. [%s != %s]", serverString, "+++"));

        client.setVerificationCode(-1);
        client.setHasPassedBotVerification(true);
        gameServer.getLogger().debug(String.format("[%s] Passed the antibot verification successfully.", client.getIpAddress()));
    }
}
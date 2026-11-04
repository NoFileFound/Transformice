package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GameServer;
import org.transformice.data.objects.PlayerConnectionInfo;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendHandshake;
import org.transformice.packets.send.informations.PacketSendLoginAllowEmail;
import org.transformice.packets.send.language.PacketSendClientVerification;
import org.transformice.packets.send.login.PacketSendLoginFlyerPopUp;
import org.transformice.packets.send.login.PacketSendSetLoginBanner;
import org.transformice.packets.send.tribulle.PacketSendTribulleAdventure;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_HANDSHAKE)
public final class PacketRecvHandshake extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int version = psData.readShort();
        String language = psData.readUTF();
        String connectionToken = psData.readUTF();
        String connectionPlatform = psData.readUTF();
        String browserInfo = psData.readUTF();
        int loaderStageSize = psData.readInt();
        String CCFData = psData.readUTF();
        String fontNameHash = psData.readUTF();
        String serverString = psData.readUTF();
        int referrerId = psData.readInt();
        int timestamp = psData.readInt();
        psData.readUTF(); // empty in Transformice
        if (version != gameServer.getConfig().getSWFConfig().getVersion() || !connectionToken.equals(gameServer.getConfig().getSWFConfig().getConnectionKey())) {
            throw new PSReceivePacketException(String.format("[%s] Mismatched SWF Data: %s | %s", client.getIpAddress(), version, connectionToken));
        }

        // Handshake
        client.setConnectionData(new PlayerConnectionInfo(connectionPlatform, browserInfo, loaderStageSize, CCFData, fontNameHash, serverString, referrerId, timestamp));
        client.sendData(new PacketSendHandshake(gameServer.getPlayers().size(), client.getCountryCode(), language, gameServer.getConfig().getSWFConfig().getAuthorizationKey(), true));
        client.setHasPassedHandShake(true);

        // Misc packets
        client.sendData(new PacketSendLoginFlyerPopUp(gameServer.getConfig().getEventConfig().getFlyerImg()));
        client.sendData(new PacketSendLoginAllowEmail(gameServer.getConfig().getGameConfig().isEnableEmailAddressLogin()));
        client.sendData(new PacketSendSetLoginBanner(gameServer.getConfig().getEventConfig().getBannerBackgroundImg(), gameServer.getConfig().getEventConfig().getBannerForegroundImg()));
        client.sendData(new PacketSendTribulleAdventure(gameServer.getConfig().getEventConfig().getBannerId()));

        // Verification
        int verCode = PSRandom.nextInt(99999999);
        client.setVerificationCode(verCode);
        client.sendData(new PacketSendClientVerification(verCode));
    }
}
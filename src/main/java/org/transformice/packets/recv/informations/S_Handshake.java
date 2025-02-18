package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.language.C_ClientVerification;
import org.transformice.packets.send.login.C_Handshake;
import org.transformice.packets.send.login.C_SetAdventureBanner;
import org.transformice.packets.send.login.C_SetAllowEmailAddress;
import org.transformice.packets.send.login.C_SetNewsPopupFlyer;
import org.transformice.packets.send.login.legacy.C_ChangeLoginAdventure;
import org.transformice.packets.send.login.legacy.C_ChangeLoginBackground;

@SuppressWarnings("unused")
public final class S_Handshake implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        short version = data.readShort();
        String language = data.readString();
        String connectionToken = data.readString();
        String playerType = data.readString();

        data.readString(); // browser info
        data.readInt(); // loader stage size.
        data.readString(); // ccfData
        data.readString(); // font name hash
        data.readString(); // server string
        data.readInt(); // referrer
        data.readInt(); // time since you opened the game.
        data.readString(); // game name, empty in Transformice

        Application.getLogger().debug(String.format("[Connection] A new connection %s was made using %s.", client.getIpAddress(), playerType));
        if(version != Application.getSwfInfo().version || !connectionToken.equals(Application.getSwfInfo().connection_key)) {
            Application.getLogger().warn(String.format("[Connection] The ip %s tried to connect with unofficial version.", client.getIpAddress()));
            client.closeConnection();
            return;
        }

        client.sendPacket(new C_Handshake(client.getServer().getPlayers().size(), language, client.getCountryLangue()));
        // TODO Implement packet: [20, 4]
        client.sendPacket(new C_SetNewsPopupFlyer());
        client.sendPacket(new C_SetAllowEmailAddress());
        if(Application.getPropertiesInfo().legacy_login) {
            client.sendPacket(new C_ChangeLoginBackground());
            client.sendPacket(new C_ChangeLoginAdventure());
        }
        else {
            client.sendPacket(new C_SetAdventureBanner());
        }

        client.verCode = SrcRandom.RandomNumber(1000000, 999999999);
        client.sendPacket(new C_ClientVerification(client.verCode));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 1;
    }
}
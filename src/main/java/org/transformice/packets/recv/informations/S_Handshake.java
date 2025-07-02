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
import org.transformice.packets.send.newpackets.C_SetAdventureBanner;
import org.transformice.packets.send.informations.C_SetAllowEmailAddress;
import org.transformice.packets.send.newpackets.C_SetNewsPopupFlyer;
import org.transformice.packets.send.tribe.C_ChangeLoginAdventure;
import org.transformice.packets.send.transformice.C_ChangeLoginBackground;
import org.transformice.packets.send.shop.C_AddAnimationLib;

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

        Application.getLogger().debug(Application.getTranslationManager().get("newconnection", client.getIpAddress(), playerType));
        if(version != Application.getSwfInfo().version || !connectionToken.equals(Application.getSwfInfo().connection_key)) {
            Application.getLogger().warn(Application.getTranslationManager().get("fakeswfconnection", client.getIpAddress()));
            client.closeConnection();
            return;
        }

        client.playerType = playerType;
        client.sendPacket(new C_Handshake(client.getServer().getPlayersCount(), language, client.getCountryLangue()));
        client.sendPacket(new C_AddAnimationLib());
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

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
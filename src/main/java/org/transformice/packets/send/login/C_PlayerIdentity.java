package org.transformice.packets.send.login;

// Imports
import static org.transformice.utils.Utils.buildLanguageMap;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.SendPacket;
import org.transformice.utils.Langue;

public final class C_PlayerIdentity implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerIdentity(Client client) {
        var info = buildLanguageMap();
        var privInfo = client.calculatePrivileges();

        this.byteArray.writeInt(client.isGuest() ? 0 : client.getAccount().getId());
        this.byteArray.writeString(client.getPlayerName());
        this.byteArray.writeInt(client.isGuest() ? 0 : client.getAccount().getPlayedTime());
        this.byteArray.writeByte(Langue.fromValue(client.playerCommunity));
        this.byteArray.writeInt(client.getSessionId());
        this.byteArray.writeBoolean(!client.isGuest());
        this.byteArray.writeByte(privInfo.size());
        for(Integer integer : privInfo) {
            this.byteArray.writeByte(integer.byteValue());
        }
        this.byteArray.writeBoolean(client.getAccount().getHasPublicAuthorization());
        this.byteArray.writeUnsignedShort(80);
        this.byteArray.writeUnsignedShort(info.size());
        for (Map.Entry<String, String> entry : info.entrySet()) {
            this.byteArray.writeString(entry.getKey());
            this.byteArray.writeString(entry.getValue());
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
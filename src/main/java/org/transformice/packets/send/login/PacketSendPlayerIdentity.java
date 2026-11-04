package org.transformice.packets.send.login;

// Imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.LanguageInfo;
import org.transformice.data.objects.GameCommunity;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerIdentity extends PSSendPacket {
    private final long globalId;
    private final String playerName;
    private final long playedTime;
    private final GameCommunity gameCommunity;
    private final int sessionId;
    private final boolean isRegistered;
    private final List<Integer> staffPrivileges;
    private final boolean hasPublicAuthorization;
    private final Map<String, String> communities;

    /**
     * Creates a new packet of SEND_PLAYER_IDENTITY.
     * @param globalId The player's id in the database.
     * @param playerName The player's name.
     * @param playedTime The player's total played time.
     * @param playerCommunity The player's game community.
     * @param sessionId The player's session id.
     * @param isRegistered True if the player has an account or else False.
     * @param staffPrivileges The player's staff privileges (if he has any).
     * @param hasPublicAuthorization True if the player can access any staff channel or else False.
     */
    public PacketSendPlayerIdentity(long globalId, String playerName, long playedTime, GameCommunity playerCommunity, int sessionId, boolean isRegistered, List<Integer> staffPrivileges, boolean hasPublicAuthorization) {
        super(PacketOpcodes.SEND_PLAYER_IDENTITY);
        this.globalId = globalId;
        this.playerName = playerName;
        this.playedTime = playedTime;
        this.gameCommunity = playerCommunity;
        this.sessionId = sessionId;
        this.isRegistered = isRegistered;
        this.staffPrivileges = staffPrivileges;
        this.hasPublicAuthorization = hasPublicAuthorization;
        this.communities = new HashMap<>();
        for (LanguageInfo language : LanguageInfo.values())
            this.communities.put(language.getCode(), language.getCountryCode());
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.globalId);
        packet.writeUTF(this.playerName, true);
        packet.writeInt(this.playedTime);
        packet.writeByte(this.gameCommunity.getId());
        packet.writeInt(this.sessionId);
        packet.writeBoolean(this.isRegistered);
        packet.writeByte(this.staffPrivileges.size());
        for (Integer privilege : this.staffPrivileges)
            packet.writeByte(privilege);

        packet.writeBoolean(this.hasPublicAuthorization);
        packet.writeUnsignedShort(80);
        packet.writeUnsignedShort(this.communities.size());
        for (Map.Entry<String, String> entry : this.communities.entrySet()) {
            packet.writeUTF(entry.getKey(), true);
            packet.writeUTF(entry.getValue(), true);
        }

        return packet;
    }
}
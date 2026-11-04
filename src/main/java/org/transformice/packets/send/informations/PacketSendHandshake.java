package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendHandshake extends PSSendPacket {
    private final int connectedPlayers;
    private final String country;
    private final String language;
    private final long authorizationKey;
    private final boolean isStreaming;

    /**
     * Creates a new packet of SEND_HANDSHAKE.
     * @param connectedPlayers Amount of connected players in the server.
     * @param country The player's country.
     * @param language The player's language.
     * @param authorizationKey The swf's authorization key.
     * @param isStreaming True if the twitch logo showing in the background or False.
     */
    public PacketSendHandshake(int connectedPlayers, String country, String language, long authorizationKey, boolean isStreaming) {
        super(PacketOpcodes.SEND_HANDSHAKE);
        this.connectedPlayers = connectedPlayers;
        this.country = country;
        this.language = language;
        this.authorizationKey = authorizationKey;
        this.isStreaming = isStreaming;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.connectedPlayers);
        packet.writeUTF(this.language, true);
        packet.writeUTF(this.country, true);
        packet.writeInt(this.authorizationKey);
        packet.writeBoolean(this.isStreaming);
        return packet;
    }
}
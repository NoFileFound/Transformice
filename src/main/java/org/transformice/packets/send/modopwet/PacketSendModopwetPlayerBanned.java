package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetPlayerBanned extends PSSendPacket {
    private final String playerName;
    private final String bannedBy;
    private final int banHours;
    private final String banReason;

    /**
     * Creates a new packet of SEND_MODOPWET_PLAYER_BANNED.
     * @param playerName The player's name.
     * @param bannedBy The moderator's name.
     * @param banHours The ban duration.
     * @param banReason The ban reason.
     */
    public PacketSendModopwetPlayerBanned(String playerName, String bannedBy, int banHours, String banReason) {
        super(PacketOpcodes.SEND_MODOPWET_PLAYER_BANNED);
        this.playerName = playerName;
        this.bannedBy = bannedBy;
        this.banHours = banHours;
        this.banReason = banReason;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeBoolean(this.banReason.equals("$MessageTriche") || this.banReason.equals("$MessageTricheDef"));
        packet.writeUTF(this.bannedBy, true);
        packet.writeInt(this.banHours);
        packet.writeUTF(this.banReason, true);
        return packet;
    }
}
package org.transformice.packets.send.modopwet;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetReportStatusDeleted extends PSSendPacket {
    private final String playerName;
    private final String deletedBy;

    /**
     * Creates a new packet of SEND_MODOPWET_REPORT_STATUS_DELETED.
     * @param playerName The player's name.
     * @param deletedBy The moderator who deleted the player's report.
     */
    public PacketSendModopwetReportStatusDeleted(String playerName, String deletedBy) {
        super(PacketOpcodes.SEND_MODOPWET_REPORT_STATUS_DELETED);
        this.playerName = playerName;
        this.deletedBy = deletedBy;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.playerName, true).writeUTF(this.deletedBy, true);
    }
}
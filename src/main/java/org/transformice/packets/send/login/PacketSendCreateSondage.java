package org.transformice.packets.send.login;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCreateSondage extends PSSendPacket {
    private final long accountId;
    private final String title;
    private final List<String> options;

    /**
     * Creates a new packet of SEND_CREATE_SONDAGE.
     * @param accountId The player's account id.
     * @param title The survey's title.
     * @param answers The survey's options.
     */
    public PacketSendCreateSondage(long accountId, String title, List<String> answers) {
        super(PacketOpcodes.SEND_CREATE_SONDAGE);
        this.accountId = accountId;
        this.title = title;
        this.options = answers;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.accountId);
        packet.writeUTF("", true);
        packet.writeBoolean(false);
        packet.writeUTF(this.title, true);
        for (String option : this.options)
            packet.writeUTF(option, true);

        return packet;
    }
}
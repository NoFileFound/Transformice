package org.transformice.packets.send.language;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.LanguageInfo;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLanguageList extends PSSendPacket {
    private final String playerLanguage;
    private final String playerLanguageCountry;

    /**
     * Creates a new packet of SEND_LANGUAGE_LIST.
     * @param playerLanguage The player's current language id.
     */
    public PacketSendLanguageList(String playerLanguage, String playerLanguageCountry) {
        super(PacketOpcodes.SEND_LANGUAGE_LIST);
        this.playerLanguage = playerLanguage;
        this.playerLanguageCountry = playerLanguageCountry;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        LanguageInfo selected = LanguageInfo.fromCode(this.playerLanguage);
        packet.writeUnsignedShort(LanguageInfo.values().length);
        packet.writeUTF(selected.getCode(), true);
        packet.writeUTF(selected.getName(), true);
        packet.writeUTF(this.playerLanguageCountry, true);
        for (LanguageInfo language : LanguageInfo.values()) {
            if (language != selected) {
                packet.writeUTF(language.getCode(), true);
                packet.writeUTF(language.getName(), true);
                packet.writeUTF(language.getCountryCode(), true);
            }
        }

        return packet;
    }
}
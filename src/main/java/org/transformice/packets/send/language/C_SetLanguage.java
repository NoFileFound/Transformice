package org.transformice.packets.send.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_SetLanguage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetLanguage(String lang) {
        var language = Application.getLanguageInfo().get(lang.toLowerCase());
        if(language == null) {
            Application.getLogger().warn(Application.getTranslationManager().get("langnotimplemented", lang));
            return;
        }

        this.byteArray.writeString(lang);
        this.byteArray.writeString(language[1]);
        this.byteArray.writeBoolean(Boolean.parseBoolean(language[2]));
        this.byteArray.writeBoolean(Boolean.parseBoolean(language[3]));
        this.byteArray.writeString(language[4]);
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
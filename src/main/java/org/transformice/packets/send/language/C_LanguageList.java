package org.transformice.packets.send.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_LanguageList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LanguageList(String countryLang) {
        var languages = Application.getLanguageInfo();
        if(!languages.containsKey(countryLang)) {
            countryLang = "en";
        }

        this.byteArray.writeUnsignedShort(languages.size()).writeString(countryLang).writeString(languages.get(countryLang)[0]).writeString(languages.get(countryLang)[1]);
        for (var entry : languages.entrySet()) {
            String countryCode = entry.getKey();
            if(!countryCode.equals(countryLang)) {
                this.byteArray.writeString(countryCode).writeString(entry.getValue()[0]).writeString(entry.getValue()[1]);
            }
        }
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
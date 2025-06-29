package org.transformice.packets.send.login;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CreateSurvey implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CreateSurvey(long type, String title, List<String> answers) {
        this.byteArray.writeInt(type);
        this.byteArray.writeString("").writeBoolean(false); // president, election
        this.byteArray.writeString(title);
        for(String answer : answers) {
            this.byteArray.writeString(answer);
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
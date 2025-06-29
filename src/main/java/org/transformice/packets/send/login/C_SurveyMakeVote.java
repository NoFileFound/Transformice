package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SurveyMakeVote implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SurveyMakeVote(byte voteOption) {
        this.byteArray.writeUnsignedByte(voteOption);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
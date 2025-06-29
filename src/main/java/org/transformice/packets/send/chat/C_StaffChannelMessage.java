package org.transformice.packets.send.chat;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_StaffChannelMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_StaffChannelMessage(int channelId, String playerName, String message) {
        this.byteArray.writeByte(channelId);
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(message);
        this.byteArray.writeBoolean(false);
        this.byteArray.writeBoolean(false);
        this.byteArray.writeUnsignedByte(0);
    }

    public C_StaffChannelMessage(int channelId, String playerName, String message, List<String> trArgs) {
        this.byteArray.writeByte(channelId);
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(message);
        this.byteArray.writeBoolean(false);
        this.byteArray.writeBoolean(true);
        this.byteArray.writeUnsignedByte(trArgs.size());
        for(String trArg : trArgs) {
            this.byteArray.writeString(trArg);
        }
    }

    public C_StaffChannelMessage(int channelId, String message, String playerCommunity, String playerName) {
        this.byteArray.writeByte(channelId);
        this.byteArray.writeString("");
        this.byteArray.writeString("• [" + playerCommunity + "] " + message);
        this.byteArray.writeBoolean(true);
        this.byteArray.writeBoolean(true);
        this.byteArray.writeUnsignedByte(1);
        this.byteArray.writeString(playerName);
    }

    @Override
    public int getC() {
        return 6;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
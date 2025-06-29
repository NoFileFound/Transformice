package org.transformice.packets.send.modopwet;

// Imports
import java.util.Deque;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChatLog implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChatLog(String playerName, Map<String, Deque<String[]>> chatMessages, Map<String, Deque<String[]>> whisperMessages) {
        if(chatMessages != null) {
            this.byteArray.writeString(playerName);
            this.byteArray.writeByte(chatMessages.size());
            for(var entry : chatMessages.entrySet()) {
                this.byteArray.writeString(entry.getKey());
                for(var info : entry.getValue()) {
                    this.byteArray.writeString(info[1]);
                    this.byteArray.writeString(info[0]);
                }
            }
        } else {
            this.byteArray.writeByte(0);
        }

        if(whisperMessages != null) {
            this.byteArray.writeByte(whisperMessages.size());
            for(var entry : whisperMessages.entrySet()) {
                this.byteArray.writeString(entry.getKey());
                this.byteArray.writeByte(entry.getValue().size());
                for(var info : entry.getValue()) {
                    this.byteArray.writeString(info[1]);
                    this.byteArray.writeString(info[0]);
                }
            }
        } else {
            this.byteArray.writeByte(0);
        }
    }

    @Override
    public int getC() {
        return 25;
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
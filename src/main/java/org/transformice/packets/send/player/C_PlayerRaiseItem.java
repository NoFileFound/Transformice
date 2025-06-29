package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerRaiseItem implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerRaiseItem(int id, int sessionId, Object[] args) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(id);
        switch (id) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
                this.byteArray.writeInt((Integer)args[0]);
                break;
            case 7:
                this.byteArray.writeString((String)args[0]);
                this.byteArray.writeUnsignedByte((Integer)args[1]);
                break;
            case 5:
                this.byteArray.writeString((String)args[0]);
                break;
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
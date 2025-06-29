package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_VisualConsumableInfo implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_VisualConsumableInfo(int id, int sessionId, Object[] args) {
        this.byteArray.writeUnsignedByte(id);
        switch (id) {
            case 1:
                this.byteArray.writeUnsignedShort((Integer)args[0]);
                this.byteArray.writeInt((Integer)args[1]);
                break;
            case 2:
                this.byteArray.writeInt(sessionId);
                this.byteArray.writeInt((Integer)args[0]);
                this.byteArray.writeInt((Integer)args[1]);
                this.byteArray.writeInt((Integer)args[2]);
                this.byteArray.writeInt((Integer)args[3]);
                this.byteArray.writeInt((Integer)args[4]);
                break;
            case 3:
                this.byteArray.writeString((String) args[0]);
                break;
            case 4:
                this.byteArray.writeInt(sessionId);
                this.byteArray.writeInt((Integer)args[0]);
                break;
            case 5:
                this.byteArray.writeInt(sessionId);
                this.byteArray.writeUnsignedShort((Integer)args[0]);
                this.byteArray.writeByte((Integer)args[1]);
                break;
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
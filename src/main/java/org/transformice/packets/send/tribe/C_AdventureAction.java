package org.transformice.packets.send.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

import java.util.List;

public final class C_AdventureAction implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AdventureAction(int advId, int actionId, List<String> args) {
        this.byteArray.writeUnsignedByte(advId);
        this.byteArray.writeUnsignedByte(actionId);
        this.byteArray.writeUnsignedByte(args.size());
        for(String arg : args) {
            this.byteArray.writeString(arg);
        }
    }

    @Override
    public int getC() {
        return 16;
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
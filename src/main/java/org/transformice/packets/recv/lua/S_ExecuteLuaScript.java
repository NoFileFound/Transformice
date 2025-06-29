package org.transformice.packets.recv.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ExecuteLuaScript implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int length = ((data.readUnsignedByte() & 0xFF) << 16) | ((data.readUnsignedByte() & 0xFF) << 8) | (data.readUnsignedByte() & 0xFF);
        String script = new String(data.readBytes(length));
        if(client.hasStaffPermission("LuaDev", "Lua_Run") || client.hasStaffPermission("Admin", "Lua_Run")) {
            client.runLuaScript(script);
        }
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}
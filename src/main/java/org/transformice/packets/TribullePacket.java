package org.transformice.packets;

public interface TribullePacket {
    short getTribulleCode();

    boolean getIsLegacy(); // [60, 1]

    byte[] getPacket();
}
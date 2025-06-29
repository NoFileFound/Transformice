package org.transformice.packets;

import java.util.Objects;
public final class PacketStruct {
    private final int code;
    private final boolean legacy;

    public PacketStruct(int code, boolean legacy) {
        this.code = code;
        this.legacy = legacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PacketStruct other)) return false;
        return code == other.code && legacy == other.legacy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, legacy);
    }
}
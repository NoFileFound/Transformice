package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLoginSouris extends PSSendPacket {
    private final int key;
    private final int value;

    /**
     * Creates a new packet of SEND_LOGIN_SOURIS.
     * @param key The provided key.
     * @param value The provided value.
     */
    public PacketSendLoginSouris(int key, int value) {
        super(PacketOpcodes.SEND_LOGIN_SOURIS);
        this.key = key;
        this.value = value;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedByte(this.key).writeUnsignedByte(this.value);
    }
}
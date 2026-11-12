package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSetCheeseSpriteSuffix extends PSSendPacket {
    private final String suffix;

    /**
     * Creates a new packet of SEND_SET_CHEESE_SPRITE_SUFFIX.
     * @param suffix The cheese's sprite suffix.
     */
    public PacketSendSetCheeseSpriteSuffix(String suffix) {
        super(PacketOpcodes.SEND_SET_CHEESE_SPRITE_SUFFIX);
        this.suffix = suffix;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.suffix, true);
    }
}
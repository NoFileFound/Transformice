package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanLookChange extends PSSendPacket {
    private final String shamanLook;

    /**
     * Creates a new packet of SEND_SHAMAN_LOOK_CHANGE.
     * @param shamanLook The shaman's look.
     */
    public PacketSendShamanLookChange(String shamanLook) {
        super(PacketOpcodes.SEND_SHAMAN_LOOK_CHANGE);
        this.shamanLook = shamanLook;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        PSByteArray packet = new PSByteArray();
        PSByteArray items = new PSByteArray();
        short count = 0;
        for (String item : this.shamanLook.split(",")) {
            int separator = item.indexOf('_');
            short realItem = Short.parseShort(separator == -1 ? item : item.substring(0, separator));
            if (realItem == 0)
                continue;

            items.writeShort(realItem);
            count++;
        }

        packet.writeShort(count);
        packet.writeBytes(items.toByteArray());
        return packet;
    }
}
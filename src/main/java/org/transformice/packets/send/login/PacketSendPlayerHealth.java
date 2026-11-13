package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerHealth extends PSSendPacket {
    private final int healthPoints;

    /**
     * Creates a new packet of SEND_PLAYER_HEALTH.
     * @param healthPoints The health points to add.
     */
    public PacketSendPlayerHealth(int healthPoints) {
        super(PacketOpcodes.SEND_PLAYER_HEALTH);
        this.healthPoints = healthPoints;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.healthPoints);
    }
}
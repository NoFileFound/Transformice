package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.AddBonusType;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendAddBonus extends PSSendPacket {
    private final AddBonusType bonusType;
    private final int posX;
    private final int posY;
    private final int angle;
    private final int bonusId;
    private final boolean isVisible;

    /**
     * Creates a new packet of SEND_ADD_BONUS.
     * @param bonusType The bonus's type.
     * @param posX The bonus's X position.
     * @param posY The bonus's Y position.
     * @param angle The bonus's angle.
     * @param bonusId The bonus's id.
     * @param isVisible Is the bonus visible or not.
     */
    public PacketSendAddBonus(AddBonusType bonusType, int posX, int posY, int angle, int bonusId, boolean isVisible) {
        super(PacketOpcodes.SEND_ADD_BONUS);
        this.bonusType = bonusType;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.bonusId = bonusId;
        this.isVisible = isVisible;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeInt128(this.bonusType.getId());
        packet.writeInt128(this.angle);
        packet.writeInt128(this.bonusId);
        packet.writeBoolean(this.isVisible);
        return packet;
    }
}
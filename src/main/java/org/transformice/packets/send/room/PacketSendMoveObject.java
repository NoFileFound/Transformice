package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMoveObject extends PSSendPacket {
    private final boolean isPhysic;
    private final int objectId;
    private final int xPosition;
    private final int yPosition;
    private final boolean positionOffset;
    private final int xSpeed;
    private final int ySpeed;
    private final boolean speedOffset;
    private final int angle;
    private final boolean angleOffset;

    /**
     * Creates a new packet of SEND_MOVE_OBJECT.
     * @param isPhysic Is the object physic.
     * @param objectId The object's id.
     * @param xPosition The X position.
     * @param yPosition The Y position.
     * @param positionOffset The position offset.
     * @param xSpeed The X speed.
     * @param ySpeed The Y speed.
     * @param speedOffset The speed offset.
     * @param angle The angle.
     * @param angleOffset The angle offset.
     */
    public PacketSendMoveObject(boolean isPhysic, int objectId, int xPosition, int yPosition, boolean positionOffset, int xSpeed, int ySpeed, boolean speedOffset, int angle, boolean angleOffset) {
        super(PacketOpcodes.SEND_MOVE_OBJECT);
        this.isPhysic = isPhysic;
        this.objectId = objectId;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.positionOffset = positionOffset;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.speedOffset = speedOffset;
        this.angle = angle;
        this.angleOffset = angleOffset;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.isPhysic);
        packet.writeInt128(this.objectId);
        packet.writeInt128(this.xPosition);
        packet.writeInt128(this.yPosition);
        packet.writeBoolean(this.positionOffset);
        packet.writeInt128(this.xSpeed);
        packet.writeInt128(this.ySpeed);
        packet.writeBoolean(this.speedOffset);
        packet.writeInt128(this.angle);
        packet.writeBoolean(this.angleOffset);
        return packet;
    }
}
package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerCollectibleAction extends PSSendPacket {
    private final int type;
    private final boolean can;
    private final int sessionId;
    private final String imgPath;
    private final int offsetX;
    private final int offsetY;
    private final boolean foreground;
    private final int sizePer;
    private final int angle;

    /**
     * Creates a new packet of SEND_PLAYER_COLLECTIBLE_ACTION.
     * @param type The action's type.
     * @param sessionId The player's session id.
     * @param imgPath The image's file.
     * @param offsetX The image's offset on X-axis.
     * @param offsetY The image's offset on Y-axis.
     * @param foreground True if the image shows the foreground or else background.
     * @param sizePer The image's size percentage.
     * @param angle The image's angle.
     */
    public PacketSendPlayerCollectibleAction(int type, int sessionId, String imgPath, int offsetX, int offsetY, boolean foreground, int sizePer, int angle) {
        super(PacketOpcodes.SEND_PLAYER_COLLECTIBLE_ACTION);
        this.type = type;
        this.can = false;
        this.sessionId = sessionId;
        this.imgPath = imgPath;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.foreground = foreground;
        this.sizePer = sizePer;
        this.angle = angle;
    }

    /**
     * Creates a new packet of SEND_PLAYER_COLLECTIBLE_ACTION.
     * @param sessionId The player's session id.
     * @param isEnabled Checks if the player can collect.
     */
    public PacketSendPlayerCollectibleAction(int sessionId, boolean isEnabled) {
        super(PacketOpcodes.SEND_PLAYER_COLLECTIBLE_ACTION);
        this.type = 1;
        this.can = isEnabled;
        this.sessionId = sessionId;
        this.imgPath = "";
        this.offsetX = 0;
        this.offsetY = 0;
        this.foreground = false;
        this.sizePer = 100;
        this.angle = 0;
    }

    /**
     * Creates a new packet of SEND_PLAYER_COLLECTIBLE_ACTION.
     * @param sessionId The player's session id.
     */
    public PacketSendPlayerCollectibleAction(int sessionId) {
        super(PacketOpcodes.SEND_PLAYER_COLLECTIBLE_ACTION);
        this.type = 3;
        this.can = false;
        this.sessionId = sessionId;
        this.imgPath = "";
        this.offsetX = 0;
        this.offsetY = 0;
        this.foreground = false;
        this.sizePer = 100;
        this.angle = 0;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedByte(this.type);
        if (this.type == 1) {
            packet.writeBoolean(this.can);
        } else {
            packet.writeInt(this.sessionId);
            if (this.type == 2) {
                packet.writeUTF(this.imgPath, true);
                packet.writeShort((short)this.offsetX);
                packet.writeShort((short)this.offsetY);
                packet.writeBoolean(this.foreground);
                packet.writeShort((short)this.sizePer);
                packet.writeShort((short)this.angle);
            }
        }

        return packet;
    }
}
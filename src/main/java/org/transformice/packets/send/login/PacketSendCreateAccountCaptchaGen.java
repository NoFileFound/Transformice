package org.transformice.packets.send.login;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.psframework.utils.PSZlib;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCreateAccountCaptchaGen extends PSSendPacket {
    private final int posX;
    private final int posY;
    private final List<String> lines;

    /**
     * Creates a new packet of SEND_CREATE_ACCOUNT_CAPTCHA_GEN.
     * @param px The position X.
     * @param py The position Y.
     * @param lines The lines to draw.
     */
    public PacketSendCreateAccountCaptchaGen(int px, int py, List<String> lines) {
        super(PacketOpcodes.SEND_CREATE_ACCOUNT_CAPTCHA_GEN);
        this.posX = px;
        this.posY = py;
        this.lines = lines;
    }

    /**
     * Creates the captcha.
     * @return The captcha as a packet.
     */
    private PSByteArray createCaptchaContent() {
        PSByteArray packet = new PSByteArray();
        packet.writeUnsignedByte(0);
        packet.writeUnsignedShort(this.posX);
        packet.writeUnsignedShort(this.posY);
        packet.writeUnsignedShort(this.posX * this.posY);
        for (String line : lines) {
            packet.writeBytes(new byte[]{0, 0, 0, 0});
            for (String value : line.split(",")) {
                packet.writeUnsignedByte(Integer.parseInt(value));
                packet.writeBytes(new byte[]{0, 0, 0});
            }

            packet.writeBytes(new byte[]{0, 0, 0, 0});
        }

        int paddingLength = ((this.posX * this.posY) - ((packet.getLength() - 6) / 4)) * 4;
        if (paddingLength > 0) {
            packet.writeBytes(new byte[paddingLength]);
        }

        return packet;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        byte[] compressed = PSZlib.compress(this.createCaptchaContent().toByteArray());
        return new PSByteArray().writeInt(compressed.length).writeBytes(compressed);
    }
}
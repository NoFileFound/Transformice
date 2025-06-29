package org.transformice.packets.send.login;

// Imports
import static org.transformice.utils.Utils.compressZlib;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CaptchaRequest implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CaptchaRequest(int px, int py, List<String> lines) {
        ByteArray captchaPacket = new ByteArray();
        captchaPacket.writeUnsignedByte(0);
        captchaPacket.writeUnsignedShort(px);
        captchaPacket.writeUnsignedShort(py);
        captchaPacket.writeUnsignedShort(px * py);

        for (String line : lines) {
            captchaPacket.writeBytes(new byte[]{0, 0, 0, 0});
            for (String value : line.split(",")) {
                captchaPacket.writeUnsignedByte(Integer.parseInt(value));
                captchaPacket.writeBytes(new byte[]{0, 0, 0});
            }
            captchaPacket.writeBytes(new byte[]{0, 0, 0, 0});
        }

        int paddingLength = ((px * py) - ((captchaPacket.getLength() - 6) / 4)) * 4;
        if (paddingLength > 0) {
            captchaPacket.writeBytes(new byte[paddingLength]);
        }

        byte[] compressed = compressZlib(captchaPacket.toByteArray());
        this.byteArray.writeInt(compressed.length);
        this.byteArray.writeBytes(compressed);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
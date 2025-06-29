package org.transformice.packets.send.player;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerTitleList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerTitleList(List<Double> titleList) {
        List<Double> titleListWithoutStars = new ArrayList<>();
        List<Double> titleListWithStars = new ArrayList<>();
        for (double num : titleList) {
            if (num % 1 == 0) {
                titleListWithoutStars.add(num);
            } else {
                titleListWithStars.add(num);
            }
        }

        this.byteArray.writeShort((short)titleListWithoutStars.size());
        for (double num : titleListWithoutStars) {
            this.byteArray.writeShort((short)num);
        }
        this.byteArray.writeShort((short)titleListWithStars.size());
        for (double num : titleListWithStars) {
            this.byteArray.writeShort((short)num);
            this.byteArray.writeByte((int)(num - Math.floor(num)));
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
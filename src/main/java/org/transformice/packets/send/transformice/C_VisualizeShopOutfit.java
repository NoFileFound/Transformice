package org.transformice.packets.send.transformice;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_VisualizeShopOutfit implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_VisualizeShopOutfit(int outfitId, String outfitLook, List<Object[]> outfitItems) {
        int priceBefore = 0;
        int priceAfter = 0;

        this.byteArray.writeShort((short)outfitId);
        this.byteArray.writeUnsignedByte(20);
        this.byteArray.writeString(outfitLook);
        this.byteArray.writeByte(outfitItems.size() + 2);

        // Free clothe.
        this.byteArray.writeInt(-1);
        this.byteArray.writeUnsignedByte(2);
        this.byteArray.writeUnsignedShort(5);
        this.byteArray.writeUnsignedShort(0);
        this.byteArray.writeUnsignedByte(3);
        this.byteArray.writeUnsignedShort(0);
        this.byteArray.writeUnsignedShort(0);

        // the fur
        var itemInfo = Application.getShopItemInfo().get("22_" + outfitLook.split(";")[0]);
        this.byteArray.writeInt(Integer.parseInt("230" + outfitLook.split(";")[0]));
        this.byteArray.writeUnsignedByte(1);
        this.byteArray.writeUnsignedShort(itemInfo.strawberry_price);
        this.byteArray.writeUnsignedShort(0);
        this.byteArray.writeUnsignedByte(3);
        this.byteArray.writeUnsignedShort(0);
        this.byteArray.writeUnsignedShort(0);

        // the look items
        for(var outfitItem : outfitItems) {
            this.byteArray.writeInt((int)outfitItem[0]);
            this.byteArray.writeUnsignedByte((boolean)outfitItem[3] ? 0 : 1);
            this.byteArray.writeUnsignedShort((int)outfitItem[5]);
            this.byteArray.writeUnsignedShort((int)outfitItem[6]);
            this.byteArray.writeUnsignedByte((boolean)outfitItem[4] ? 3 : String.valueOf(outfitItem[1]).isEmpty() ? 0 : 1);
            this.byteArray.writeUnsignedShort(0);
            this.byteArray.writeUnsignedShort(0);

            priceBefore += (int)outfitItem[5];
            priceAfter += ((boolean)outfitItem[3] ? 0 : (int)outfitItem[6]) + 20;
        }

        this.byteArray.writeShort((short)priceBefore);
        this.byteArray.writeShort((short)priceAfter);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
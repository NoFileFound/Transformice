package org.transformice.packets.recv.shop;

// Imports
import java.util.ArrayList;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SHOP_CUSTOMIZE_SHAMAN_ITEM)
public final class PacketRecvShopCustomizeShamanItem extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        short shopShamanItemId = psData.readShort();
        int length = psData.readByte();
        ArrayList<Integer> colors = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
            colors.add(psData.readInt());

        gameServer.getParseShop().sendCustomizeShamanItem(client.getPlayer(), shopShamanItemId, colors);
    }
}
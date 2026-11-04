package org.transformice.packets.recv.shop;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SHOP_PURCHASE_SHAMAN_ITEM)
public final class PacketRecvShopPurchaseShamanItem extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        gameServer.getParseShop().sendPurchaseShamanItem(client.getPlayer(), psData.readShort(), psData.readBoolean());
    }
}
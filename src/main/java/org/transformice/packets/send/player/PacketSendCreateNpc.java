package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCreateNpc extends PSSendPacket {
    private final int npcId;
    private final String npcName;
    private final int npcTitle;
    private final boolean isFeminine;
    private final String npcLook;
    private final int x;
    private final int y;
    private final int npcEmote;
    private final boolean isFacingRight;
    private final boolean isFacePlayer;
    private final int npcInterface;
    private final String npcMessage;

    /**
     * Creates a new packet of SEND_CREATE_NPC.
     * @param npcId The npc's id.
     * @param npcName The npc's name.
     * @param npcTitle The npc's title.
     * @param isFeminine Is the NPC a woman.
     * @param npcLook The npc's outfit.
     * @param x The X position.
     * @param y The Y position.
     * @param npcEmote The npc's emote.
     * @param isFacingRight Is the npc facing right or left.
     * @param isFacePlayer is the npc facing the player.
     * @param npcInterface The npc's interface.
     * @param npcMessage The npc's message.
     */
    public PacketSendCreateNpc(int npcId, String npcName, int npcTitle, boolean isFeminine, String npcLook, int x, int y, int npcEmote, boolean isFacingRight, boolean isFacePlayer, int npcInterface, String npcMessage) {
        super(PacketOpcodes.SEND_CREATE_NPC);
        this.npcId = npcId;
        this.npcName = npcName;
        this.npcTitle = npcTitle;
        this.isFeminine = isFeminine;
        this.npcLook = npcLook;
        this.x = x;
        this.y = y;
        this.npcEmote = npcEmote;
        this.isFacingRight = isFacingRight;
        this.isFacePlayer = isFacePlayer;
        this.npcInterface = npcInterface;
        this.npcMessage = npcMessage;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.npcId);
        packet.writeUTF(this.npcName, true);
        packet.writeShort((short)this.npcTitle);
        packet.writeBoolean(this.isFeminine);
        packet.writeUTF(this.npcLook, true);
        packet.writeInt128(this.x);
        packet.writeInt128(this.y);
        packet.writeInt128(this.npcEmote);
        packet.writeBoolean(this.isFacingRight);
        packet.writeBoolean(this.isFacePlayer);
        packet.writeByte(this.npcInterface);
        packet.writeUTF(this.npcMessage, true);
        return packet;
    }
}
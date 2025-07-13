package org.transformice.packets.send.player;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.database.embeds.Adventure;
import org.transformice.packets.SendPacket;

public final class C_PlayerOpenAdventureInterface implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerOpenAdventureInterface(List<Adventure> adventureList, String playerName, String playerLook, int adventurePoints, int unlockedTitles, int unlockedBadges) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(playerLook);
        this.byteArray.writeInt(adventurePoints);
        this.byteArray.writeShort((short) unlockedTitles);
        this.byteArray.writeShort((short) unlockedBadges);
        this.byteArray.writeUnsignedShort(adventureList.size());
        for(Adventure adventure : adventureList) {
            this.byteArray.writeUnsignedShort(adventure.getAdventureId());
            this.byteArray.writeUnsignedByte(1);
            this.byteArray.writeUnsignedShort(adventure.getBannerId());
            this.byteArray.writeInt(adventure.getDateDiscovered());
            this.byteArray.writeInt(adventure.getAdventurePoints());
            this.byteArray.writeBoolean(adventure.getAdventurePoints() == Application.getPropertiesInfo().event.event_points);
            this.byteArray.writeByte(Application.getPropertiesInfo().event.adventure_tasks.size());

            int i = 0;
            for(var task : Application.getPropertiesInfo().event.adventure_tasks) {
                this.byteArray.writeByte(1);
                this.byteArray.writeBoolean(false);
                this.byteArray.writeShort((short)task.task_consumable_id);
                this.byteArray.writeInt(task.task_finish_points);
                this.byteArray.writeBoolean(adventure.getAdventureTasks().get(i).isFinished());
                this.byteArray.writeUnsignedByte(task.task_progess_type);
                if(task.task_progess_type == 1) {
                    this.byteArray.writeInt(adventure.getAdventureTasks().get(i).getTaskProgress());
                    this.byteArray.writeInt(task.task_progess_type2_minimum);
                }
                else if(task.task_progess_type == 2) {
                    this.byteArray.writeByte(task.task_progess_type1_tooltip_id);
                }

                i++;
            }

            this.byteArray.writeByte(Application.getPropertiesInfo().event.adventure_progress.size());
            i = 0;
            for(var progress : Application.getPropertiesInfo().event.adventure_progress) {
                this.byteArray.writeByte(1);
                this.byteArray.writeBoolean(false);
                this.byteArray.writeShort(progress.shortValue());
                this.byteArray.writeInt(adventure.getAdventureProgress().get(i));
                i++;
            }
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 70;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}
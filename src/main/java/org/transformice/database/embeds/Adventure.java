package org.transformice.database.embeds;

// Imports
import dev.morphia.annotations.Embedded;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embedded
public class Adventure {
    private final int adventureId;
    private final int bannerId;
    private final long dateDiscovered;
    @Setter private int adventurePoints;
    private final ArrayList<AdventureTask> adventureTasks;
    private final ArrayList<Integer> adventureProgress;

    public Adventure(int adventureId, int bannerId, long dateDiscovered) {
        this.adventureId = adventureId;
        this.bannerId = bannerId;
        this.dateDiscovered = dateDiscovered;
        this.adventurePoints = 0;
        this.adventureTasks = new ArrayList<>();
        this.adventureProgress = new ArrayList<>();
    }

    @Getter
    @Embedded
    public static class AdventureTask {
        public int taskProgress;
        public int task_item_id;
        public boolean isFinished;

        public AdventureTask(int task_id) {
            this.taskProgress = 0;
            this.isFinished = false;
            this.task_item_id = task_id;
        }
    }
}
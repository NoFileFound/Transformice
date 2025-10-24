package org.transformice.database.embeds;

// Imports
import dev.morphia.annotations.Embedded;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.transformice.properties.configs.PropertiesConfig.PropertiesClass.EventAdventureTasks;

@Getter
@Embedded
public class Adventure {
    private final int adventureId;
    private final int bannerId;
    private final long dateDiscovered;
    @Setter private int adventurePoints;
    private final int adventurePointsServer;
    private final ArrayList<EventAdventureTasks> adventureTasksServer;
    private final ArrayList<AdventureTask> adventureTasks;
    private final ArrayList<Integer> adventureProgress;
    private final ArrayList<Integer> adventureProgressServer;

    public Adventure(int adventureId, int bannerId, long dateDiscovered, int adventurePointsServer, ArrayList<EventAdventureTasks> adventureTasksServer, ArrayList<Integer> adventureProgressServer) {
        this.adventureId = adventureId;
        this.bannerId = bannerId;
        this.dateDiscovered = dateDiscovered;
        this.adventurePoints = 0;
        this.adventurePointsServer = adventurePointsServer;
        this.adventureTasks = new ArrayList<>();
        this.adventureProgress = new ArrayList<>();
        this.adventureTasksServer = adventureTasksServer;
        this.adventureProgressServer = adventureProgressServer;
    }

    @Getter
    @Embedded
    public static class AdventureTask {
        public int taskProgress;
        public int task_item_id;
        public boolean isFinished;
        public boolean isPrized;
        public int lastPrizedTaskId;

        public AdventureTask(int task_id) {
            this.taskProgress = 0;
            this.isFinished = false;
            this.task_item_id = task_id;
            this.isPrized = false;
            this.lastPrizedTaskId = 0;
        }
    }
}
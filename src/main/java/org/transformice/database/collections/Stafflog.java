package org.transformice.database.collections;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.List;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.transformice.database.DBManager;

@Entity(value = "stafflog", useDiscriminator = false)
@Getter
public final class Stafflog {
    @Id private ObjectId id; // default
    private final String playerName;
    private final String command;
    private final Long timestamp;
    private final String arguments;

    /**
     * Creates a new action in the staff log.
     * @param playerName The player name.
     * @param command The command.
     * @param args The command args.
     */
    public Stafflog(final String playerName, final String command, final List<String> args) {
        this.playerName = playerName;
        this.command = command;
        this.timestamp = getUnixTime();
        this.arguments = String.join(", ", args);
        this.save();
    }

    /**
     * Saves an action from the stafflog.
     */
    private void save() {
        DBManager.saveInstance(this);
    }
}
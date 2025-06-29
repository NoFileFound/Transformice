package org.transformice.database.embeds;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.transformice.database.DBManager;

@Getter
@Embedded
public class CafePost {
    @Setter @Id private long id;
    final private String message;
    final private String author;
    final private Long authorId;
    @Setter private int state;
    @Setter private String moderator;
    final private Long date;
    @Setter private Short points;
    private final List<Long> votes;

    public CafePost(String message, String author, Long authorId) {
        this.id = DBManager.getCounterValue("lastCafePostId");
        this.message = message;
        this.author = author;
        this.authorId = authorId;
        this.state = 0;
        this.moderator = "";
        this.date = getUnixTime();
        this.points = 0;
        this.votes = new ArrayList<>();
    }
}
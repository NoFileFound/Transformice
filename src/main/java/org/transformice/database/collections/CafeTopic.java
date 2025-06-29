package org.transformice.database.collections;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.transformice.database.DBManager;
import org.transformice.database.embeds.CafePost;

@Entity(value = "cafetopics", useDiscriminator = false)
@Getter
public final class CafeTopic {
    @Id private final long id;
    private final String title;
    private final String author;
    private final Long authorId;
    private final String community;
    private final List<CafePost> posts;
    private final Long date;
    @Setter private int reportScore;
    private final List<Long> reporters;

    /**
     * Creates a new topic in the cafe.
     * @param title Topic title.
     * @param author Topic author.
     * @param authorId Topic author id.
     * @param community Topic author's community.
     */
    public CafeTopic(final String title, final String author, final Long authorId, final String community) {
        this.id = DBManager.getCounterValue("lastCafeTopicId");
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.community = community;
        this.date = getUnixTime();
        this.posts = new ArrayList<>();
        this.reportScore = 0;
        this.reporters = new ArrayList<>();
    }

    /**
     * Deletes a cafe topic from the database.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Saves a cafe topic in the database.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
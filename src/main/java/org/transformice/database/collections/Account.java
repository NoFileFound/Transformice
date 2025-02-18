package org.transformice.database.collections;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.transformice.database.DBManager;

@Entity(value = "accounts", useDiscriminator = false)
@Getter
public class Account {
    private final @Id long id;
    private final String playerName;
    private String emailAddress;
    private String password;
    private Integer privLevel;
    private List<String> staffRoles;
    private final String betaInviter;
    private Integer playedTime;
    private final boolean hasPublicAuthorization;

    /**
     * Creates a new player.
     * @param playerName Nickname.
     * @param emailAddress Email address.
     * @param password Password.
     * @param betaInviter Who invited him (only in beta versions).
     */
    public Account(String playerName, String emailAddress, String password, String betaInviter) {
        this.id = DBManager.getCounterValue("lastPlayerId");
        this.playerName = playerName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.privLevel = 1;
        this.staffRoles = new ArrayList<>();
        this.betaInviter = betaInviter;
        this.playedTime = 0;
        this.hasPublicAuthorization = false;
    }

    /**
     * Deletes a player from database.
     */
    public void delete() {
        DBManager.deleteInstance(this);
    }

    /**
     * Updates the database of player.
     */
    public void save() {
        DBManager.saveInstance(this);
    }
}
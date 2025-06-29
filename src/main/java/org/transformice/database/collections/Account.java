package org.transformice.database.collections;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.transformice.Application;
import org.transformice.database.DBManager;
import org.transformice.database.embeds.Quest;
import org.transformice.database.embeds.TribeRank;
import org.transformice.libraries.Pair;

@Entity(value = "accounts", useDiscriminator = false)
@Getter
public class Account {
    private final @Id long id;
    private final String playerName;
    @Setter private String emailAddress;
    @Setter private String password;
    @Setter private long avatarId;
    @Setter private Integer privLevel;
    private final List<String> staffRoles;
    private final String betaInviter;
    @Setter private Long playedTime;
    private final Boolean hasPublicAuthorization;
    private Long vipTime;
    @Setter private Short cafeBadReputation;
    @Setter private Byte playerGender;
    private final Long regDate;
    @Setter private Integer lastOn;
    @Setter private String soulmate;
    private final List<String> friendList;
    private final List<String> ignoredList;
    @Setter private String tribeName;
    @Setter private TribeRank tribeRank;
    @Setter private Integer shopCheeses;
    @Setter private Integer shopStrawberries;
    private final Map<Integer, Integer> shopBadges;
    private final List<Integer> purchasedEmojis;
    private final Map<String, Integer> inventory;
    private final List<Integer> equippedConsumables;
    @Setter private Short playerKarma;
    private final List<String> modoCommunities;
    @Setter private String mouseLook;
    @Setter private String shamanLook;
    @Setter private boolean isVerifiedEmail;
    private final List<Double> titleList;
    @Setter private Double currentTitle;
    @Setter private Integer normalSaves;
    @Setter private Integer normalSavesNoSkills;
    @Setter private Integer hardSaves;
    @Setter private Integer hardSavesNoSkill;
    @Setter private Integer divineSaves;
    @Setter private Integer divineSavesNoSkill;
    @Setter private Integer shamanCheeseCount;
    @Setter private Integer firstCount;
    @Setter private Integer cheeseCount;
    @Setter private Integer bootcampCount;
    @Setter private Integer shamanLevel;
    @Setter private Integer shamanLevelXp;
    @Setter private Integer shamanLevelNextXp;
    private final List<Integer> shamanBadges;
    @Setter private Integer equippedShamanBadge;
    private final List<String> shopClothes;
    private final Map<Integer, String> shopItems;
    private final Map<Integer, String> shopShamanItems;
    private final List<Integer> favoritedItems;
    private final List<String> shopGifts;
    private final List<String> shopMessages;
    @Setter private Integer mouseColor;
    @Setter private Integer shamanColor;
    @Setter private String lastIPAddress;
    @Setter private int shamanType;
    @Setter private boolean shamanNoSkills;
    private final List<Quest> playerMissions;
    private final Map<Integer, Integer> playerSkills;
    @Setter private Object[] totemInfo;
    @Setter private int petType;
    @Setter private long lastPetTime;
    private final List<String> letters;
    private final int[] racingStats;
    private final int[] survivorStats;
    private final int[] defilanteStats;
    @Setter private int adventurePoints;

    /**
     * Creates a new player.
     * @param playerName Nickname.
     * @param emailAddress Email address.
     * @param password Password.
     * @param betaInviter Who invited him (only in beta versions).
     */
    public Account(final String playerName, final String emailAddress, final String password, final String betaInviter, boolean isGuest) {
        if(!isGuest) this.id = DBManager.getCounterValue("lastPlayerId");
        else this.id = 0;
        this.playerName = playerName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.avatarId = 0;
        this.privLevel = 1;
        this.staffRoles = new ArrayList<>();
        this.betaInviter = betaInviter;
        this.playedTime = 0L;
        this.hasPublicAuthorization = false;
        this.vipTime = 0L;
        this.cafeBadReputation = 0;
        this.playerGender = 0;
        this.regDate = getUnixTime();
        this.lastOn = 0;
        this.soulmate = "";
        this.friendList = new ArrayList<>();
        this.ignoredList = new ArrayList<>();
        this.tribeName = "";
        this.tribeRank = new TribeRank();
        this.shopCheeses = Application.getPropertiesInfo().constants.default_shop_cheeses;
        this.shopStrawberries = Application.getPropertiesInfo().constants.default_shop_fraises;
        this.shopBadges = new HashMap<>();
        this.purchasedEmojis = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.equippedConsumables = new ArrayList<>();
        this.playerKarma = 0;
        this.modoCommunities = new ArrayList<>();
        this.mouseLook = "1;0,0,0,0,0,0,0,0,0,0,0,0";
        this.isVerifiedEmail = false;
        this.titleList = new ArrayList<>(List.of(0.0));
        this.currentTitle = 0.0;
        this.normalSaves = Application.getPropertiesInfo().constants.default_normal_saves;
        this.normalSavesNoSkills = Application.getPropertiesInfo().constants.default_normal_saves_noskill;
        this.hardSaves = Application.getPropertiesInfo().constants.default_hard_saves;
        this.hardSavesNoSkill = Application.getPropertiesInfo().constants.default_hard_saves_noskill;
        this.divineSaves = Application.getPropertiesInfo().constants.default_divine_saves;
        this.divineSavesNoSkill = Application.getPropertiesInfo().constants.default_divine_saves_noskill;
        this.shamanCheeseCount = Application.getPropertiesInfo().constants.default_shaman_cheeses;
        this.cheeseCount = Application.getPropertiesInfo().constants.default_cheeses;
        this.firstCount = Application.getPropertiesInfo().constants.default_firsts;
        this.bootcampCount = Application.getPropertiesInfo().constants.default_bootcamps;
        this.shamanLevel = 0;
        this.shamanLevelXp = 0;
        this.shamanLevelNextXp = 32;
        this.shopClothes = new ArrayList<>();
        this.shopItems = new HashMap<>();
        this.shopShamanItems = new HashMap<>();
        this.mouseColor = 7886906; // 0x78583A
        this.shamanColor = 9820630; // 0x95d9d6
        this.shamanLook = "0,0,0,0,0,0,0,0,0";
        this.shamanBadges = new ArrayList<>();
        this.equippedShamanBadge = 0;
        this.favoritedItems = new ArrayList<>();
        this.shopGifts = new ArrayList<>();
        this.shopMessages = new ArrayList<>();
        this.lastIPAddress = "";
        this.shamanType = 0;
        this.shamanNoSkills = false;
        this.playerMissions = new ArrayList<>();
        this.playerSkills = new HashMap<>();
        this.totemInfo = new Object[]{0, ""};
        this.petType = -1;
        this.lastPetTime = -1;
        this.letters = new ArrayList<>();
        this.racingStats = new int[4];
        this.survivorStats = new int[4];
        this.defilanteStats = new int[3];
        this.adventurePoints = 0;
    }

    /**
     * Checks if current player has vip.
     */
    public boolean checkVipStatus() {
        if(this.vipTime > 0) {
            if(this.vipTime < getUnixTime()) {
                this.vipTime = 0L;
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the title id and title stars from the current title.
     * @return The title id and stars.
     */
    public Pair<Short, Integer> getCurrentTitleInfo() {
        short titleNumber = this.currentTitle.shortValue();
        int titleStars = (int) Math.round((this.currentTitle - this.currentTitle.shortValue()) * 100) / 10;

        return new Pair<>(titleNumber, titleStars);
    }

    /**
     * Gets the vip time in seconds.
     * @return The vip time in seconds.
     */
    public long getVipTime() {
        return this.vipTime - getUnixTime();
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
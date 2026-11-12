package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import org.transformice.utils.Utils;

@Entity("tribes")
public class TribeEntry {
    @Id private int id;
    private String tribeName;
    private String tribeOwner;
    private Long tribeCreationDate;
    private List<String> tribeMembers;
    private List<TribeRank> tribeRanks;
    private List<TribeHistoricEntry> tribeHistory;
    public Integer tribeHouseMap;
    public String tribeLeader;
    public String tribeMessage;

    /**
     * Creates a new tribe.
     */
    protected TribeEntry() {
        // Required by Morphia.
        this.tribeMembers = new ArrayList<>();
        this.tribeCreationDate = Utils.getUnixTime();
        this.tribeRanks = new ArrayList<>();
        this.tribeHistory = new ArrayList<>();
    }

    /**
     * Creates a new tribe.
     * @param tribeName The tribe's name.
     * @param tribeOwner The tribe owner's name.
     */
    public TribeEntry(final int tribeId, final String tribeName, final String tribeOwner) {
        this();
        this.id = tribeId;
        this.tribeName = tribeName;
        this.tribeOwner = tribeOwner;
        this.tribeLeader = tribeOwner;
        this.tribeHouseMap = 0;
        this.tribeMessage = "";
        this.tribeMembers.add(tribeLeader);
        this.tribeRanks.add(new TribeRank("${trad#TG_0}", 0, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_1}", 1, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_2}", 2, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_3}", 3, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_4}", 4, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_5}", 5, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_6}", 6, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_7}", 7, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_8}", 8, 0));
        this.tribeRanks.add(new TribeRank("${trad#TG_9}", 9, 10)); // leader
        this.tribeHistory.add(new TribeHistoricEntry(1, String.format("{\"auteur\":\"%s\",\"tribu\":\"%s\"}", tribeLeader, this.tribeName)));
    }

    /**
     * Gets the tribe's id.
     * @return The tribe's id.
     */
    public int getTribeId() {
        return this.id;
    }

    /**
     * Gets the tribe's name.
     * @return The tribe's name.
     */
    public String getTribeName() {
        return this.tribeName;
    }

    /**
     * Gets the tribe's creation date.
     * @return The tribe's creation date.
     */
    public Long getTribeCreationDate() {
        return this.tribeCreationDate;
    }

    /**
     * Gets the tribe's history.
     * @return The tribe's history.
     */
    public List<TribeHistoricEntry> getTribeHistory() {
        return tribeHistory;
    }

    /**
     * Gets the tribe member list.
     * @return The tribe member list.
     */
    public List<String> getTribeMembers() {
        return this.tribeMembers;
    }

    /**
     * Gets the tribe's owner.
     * @return The tribe's owner.
     */
    public String getTribeOwner() {
        return this.tribeOwner;
    }

    /**
     * Gets the tribe rank list.
     * @return The tribe rank list.
     */
    public List<TribeRank> getTribeRanks() {
        return this.tribeRanks;
    }

    /**
     * Tribe history class.
     */
    @Embedded
    public static class TribeHistoricEntry {
        private int type;
        private int date;
        private String information;

        /**
         * Creates a new tribe history.
         */
        protected TribeHistoricEntry() {
            // Required by Morphia.
        }

        /**
         * Creates a new tribe history.
         * @param type The history type.
         * @param information The history information.
         */
        public TribeHistoricEntry(int type, String information) {
            this();
            this.type = type;
            this.date = (int) (System.currentTimeMillis() / 60000);
            this.information = information;
        }

        /**
         * Gets the history date.
         * @return The history date.
         */
        public int getDate() {
            return this.date;
        }

        /**
         * Gets the history type.
         * @return The history type.
         */
        public int getType() {
            return this.type;
        }

        /**
         * Gets the history information.
         * @return The history information.
         */
        public String getInformation() {
            return this.information;
        }
    }

    /**
     * Tribe rank.
     */
    @Embedded
    public static class TribeRank {
        private final boolean[] perms = new boolean[10];
        public int position;
        public String name;

        /**
         * Creates a new tribe rank.
         */
        public TribeRank() {
            // Required by Morphia.
            this.name = "";
        }

        /**
         * Creates a new tribe rank.
         * @param name The tribe's rank.
         * @param pos The tribe's rank position.
         * @param permCount The tribe's rank permissions.
         */
        public TribeRank(String name, int pos, int permCount) {
            this.name = name;
            this.position = pos;
            for(int i = 0; i < permCount; i++) {
                this.perms[i] = true;
            }
        }

        /**
         * Sets a permission to the rank.
         * @param position The permission id.
         * @param value True if enable the permission or else False.
         */
        public void setPerm(int position, boolean value) {
            this.perms[position] = value;
        }

        /**
         * Gets the rank's power.
         * @return The rank's power.
         */
        public int getPerms() {
            int perm = 0;
            for (int index = 0; index < this.perms.length; index++) {
                if (this.perms[index]) {
                    perm |= 1 << (index + 1);
                }
            }

            return perm;
        }

        /**
         * Checks if the rank has the given permission.
         * @param perm The permission to check.
         * @return True if the tribe rank has the permission or else False.
         */
        public boolean hasPerm(TribePerms perm) {
            return this.perms[perm.getValue()];
        }

        /**
         * The tribe's permissions.
         */
        public enum TribePerms {
            FORUM_MANAGEMENT(0),
            LOAD_MAP(1),
            CHANGE_TRIBE_HOUSE_MAP(2),
            PLAY_MUSIC(3),
            EXCLUDE_MEMBERS(4),
            INVITE_MEMBERS(5),
            CHANGE_MEMBERS_RANK(6),
            EDIT_GLOBAL_RANKS(7),
            EDIT_TRIBE_MESSAGE(8),
            LEADER(9);

            private final int value;
            TribePerms(int i) {
                this.value = i;
            }

            public int getValue() {
                return this.value;
            }
        }
    }
}
package org.transformice.database.embeds;

// Imports
import dev.morphia.annotations.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embedded
public class TribeRank {
    @Getter @Setter private int position;
    @Getter @Setter private String name;
    private final boolean[] perms = new boolean[10];

    public TribeRank() {
        this.name = "";
    }

    public TribeRank(String name, int pos, int permCount) {
        this.name = name;
        this.position = pos;
        for(int i = 0; i < permCount; i++) {
            this.perms[i] = true;
        }
    }

    public void setPerm(int position, boolean value) {
        this.perms[position] = value;
    }

    public int getPerms() {
        int perm = 0;
        for (int index = 0; index < this.perms.length; index++) {
            if (this.perms[index]) {
                perm |= 1 << (index + 1);
            }
        }

        return perm;
    }

    public boolean hasPerm(TribePerms perm) {
        return this.perms[perm.getValue()];
    }

    @Getter
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
    }
}
package org.transformice.packets.send.player;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.database.collections.Account;
import org.transformice.packets.SendPacket;

public final class C_Profile implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Profile(Account account, boolean isOnline) {
        this.byteArray.writeString(account.getPlayerName());
        this.byteArray.writeInt(isOnline ? account.getAvatarId() : 0);
        this.byteArray.writeInt(account.getRegDate());
        this.byteArray.writeByte(this.getProfileColorId(account.getStaffRoles()));
        this.byteArray.writeByte(account.getPlayerGender());
        this.byteArray.writeString(account.getTribeName());
        this.byteArray.writeString(account.getSoulmate());
        this.byteArray.writeInt(account.getNormalSaves());
        this.byteArray.writeInt(account.getShamanCheeseCount());
        this.byteArray.writeInt(account.getFirstCount());
        this.byteArray.writeInt(account.getCheeseCount());
        this.byteArray.writeInt(account.getHardSaves());
        this.byteArray.writeInt(account.getBootcampCount());
        this.byteArray.writeInt(account.getDivineSaves());
        this.byteArray.writeInt(account.getNormalSavesNoSkills());
        this.byteArray.writeInt(account.getHardSavesNoSkill());
        this.byteArray.writeInt(account.getDivineSavesNoSkill());
        this.byteArray.writeShort(account.getCurrentTitle().shortValue());
        this.byteArray.writeShort((short) account.getTitleList().size());
        for (Double title : account.getTitleList()) {
            short titleNumber = title.shortValue();
            int titleStars = (int) Math.round((title - title.intValue()) * 100) / 10;

            this.byteArray.writeShort(titleNumber);
            this.byteArray.writeByte(titleStars);
        }
        this.byteArray.writeString(account.getMouseLook() + ";" + Integer.toHexString(account.getMouseColor()));
        this.byteArray.writeShort(account.getShamanLevel().shortValue());

        this.byteArray.writeUnsignedShort(account.getShopBadges().size() * 2);
        for (var badge : account.getShopBadges().entrySet()) {
            this.byteArray.writeUnsignedShort(badge.getKey());
            this.byteArray.writeUnsignedShort(badge.getValue());
        }

        this.byteArray.writeByte(11);

        /// 	Play 1,500 rounds
        this.byteArray.writeUnsignedByte(30);
        this.byteArray.writeInt(account.getRacingStats()[0]);
        this.byteArray.writeInt(1500);
        this.byteArray.writeShort((short)124);

        /// 	Complete 10,000 rounds
        this.byteArray.writeUnsignedByte(31);
        this.byteArray.writeInt(account.getRacingStats()[1]);
        this.byteArray.writeInt(10000);
        this.byteArray.writeShort((short)125);

        /// 	Get 1st, 2nd, or 3rd positions 10,000 times
        this.byteArray.writeUnsignedByte(33);
        this.byteArray.writeInt(account.getRacingStats()[2]);
        this.byteArray.writeInt(10000);
        this.byteArray.writeShort((short)127);

        /// 	Get 10,000 firsts
        this.byteArray.writeUnsignedByte(32);
        this.byteArray.writeInt(account.getRacingStats()[3]);
        this.byteArray.writeInt(10000);
        this.byteArray.writeShort((short)126);

        /// 	Play 1,000 rounds
        this.byteArray.writeUnsignedByte(26);
        this.byteArray.writeInt(account.getSurvivorStats()[0]);
        this.byteArray.writeInt(1500);
        this.byteArray.writeShort((short)120);

        /// 	Be the shaman 800 times
        this.byteArray.writeUnsignedByte(27);
        this.byteArray.writeInt(account.getSurvivorStats()[1]);
        this.byteArray.writeInt(800);
        this.byteArray.writeShort((short)121);

        /// 	Kill 20,000 mice
        this.byteArray.writeUnsignedByte(28);
        this.byteArray.writeInt(account.getSurvivorStats()[2]);
        this.byteArray.writeInt(20000);
        this.byteArray.writeShort((short)122);

        /// 	Survive 10,000 rounds
        this.byteArray.writeUnsignedByte(29);
        this.byteArray.writeInt(account.getSurvivorStats()[3]);
        this.byteArray.writeInt(10000);
        this.byteArray.writeShort((short)123);

        /// 	Play 1,500 rounds
        this.byteArray.writeUnsignedByte(42);
        this.byteArray.writeInt(account.getDefilanteStats()[0]);
        this.byteArray.writeInt(1500);
        this.byteArray.writeShort((short)288);

        /// 	Complete 10,000 rounds
        this.byteArray.writeUnsignedByte(43);
        this.byteArray.writeInt(account.getDefilanteStats()[1]);
        this.byteArray.writeInt(10000);
        this.byteArray.writeShort((short)287);

        /// 	Gather 100,000 points
        this.byteArray.writeUnsignedByte(44);
        this.byteArray.writeInt(account.getDefilanteStats()[2]);
        this.byteArray.writeInt(100000);
        this.byteArray.writeShort((short)286);

        this.byteArray.writeUnsignedByte(account.getEquippedShamanBadge());
        this.byteArray.writeUnsignedByte(account.getShamanBadges().size());
        for (int badge : account.getShamanBadges()) {
            this.byteArray.writeUnsignedByte(badge);
        }
        this.byteArray.writeBoolean(isOnline);
        this.byteArray.writeInt(isOnline ? account.getAdventurePoints() : 0);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }

    /**
     * Gets the profile color byte.
     *
     * @param staffRoles The player's staff roles.
     * @return Profile color.
     */
    private byte getProfileColorId(List<String> staffRoles) {
        if(staffRoles.contains("Admin")) return 10; // Admin
        else if(staffRoles.contains("PublicModo")) return 5;  // Public moderator
        else if(staffRoles.contains("MapCrew")) return 11;  // MapCrew
        else if(staffRoles.contains("FunCorp")) return 13;  // FunCorp
        else if(staffRoles.contains("Sentinelle")) return 7;  // Sentinel (color does not display anymore for some reason).
        return 1;
    }
}
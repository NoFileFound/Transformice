package org.transformice.data.objects;

public enum MissionType {
    BRING_BACK_CHEESE(1),
    SAVE_MICE(2),
    KILL_MICE_IN_SURVIVOR(3),
    FINISH_MAPS_IN_RACING(4),
    FINISH_MAPS_IN_DEFILANTE(5),
    BUY_SHOP_ITEM(6),
    BRING_BACK_CHEESE_AS_SHAMAN(7),
    HALLOWEEN_COLLECT_CANDIES(10),
    HALLOWEEN_GAMBLE(11),
    HALLOWEEN_COLLECT_CAT_SKULLS(12),
    HALLOWEEN_COLLECT_BROOMS(13),
    HALLOWEEN_COLLECT_DOOR_HANDLES(14),
    CHRISTMAS_COLLECT_PRESENTS(15),
    CHRISTMAS_TRADE_PRESENTS(16),
    CHIRSTMAS_COLLECT_SNOWBOARDS(17),
    CHRISTMAS_COLLECT_SOULMATE_COINS(18),
    CHRISTMAS_TRAVEL_METERS_ON_SNOWBOARD(19),
    CHRISTMAS_PICK_UP_SNOWBALLS(20),
    VALENTINE_KISS_YOUR_SOULMATE(23),
    FINISH_20_DAILY_QUESTS(60801);

    private final int value;
    MissionType(int value) {
        this.value = value;
    }

    public static MissionType parseFrom(int i) {
        for (MissionType type : values()) {
            if (type.getId() == i)
                return type;
        }

        return null;
    }

    public int getId() {
        return this.value;
    }
}
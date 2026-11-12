package org.transformice.data.objects;

public enum AddBonusType {
    BONUS_POINT(0),
    BONUS_SPEED(1),
    BONUS_DEATH(2),
    BONUS_SPRING(3),
    BONUS_EASTER_EGG(4),
    BONUS_BOOSTER(5),
    BONUS_DISINTEGRATION(6);

    private final int value;
    AddBonusType(int value) {
        this.value = value;
    }

    public int getId() {
        return this.value;
    }

    public static AddBonusType parseFrom(int bonusType) {
        for (AddBonusType type : values()) {
            if (type.value == bonusType)
                return type;
        }

        return BONUS_POINT;
    }
}
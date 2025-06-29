package org.transformice.utils;

@lombok.Getter
public enum Langue {
    EN(0),
    FR(1),
    RU(2),
    BR(3),
    ES(4),
    CN(5),
    TR(6),
    VK(7),
    PL(8),
    HU(9),
    NL(10),
    RO(11),
    ID(12),
    DE(13),
    E2(14),
    AR(15),
    PH(16),
    LT(17),
    JP(18),
    CH(19),
    FI(20),
    CZ(21),
    SK(22),
    HR(23),
    BG(24),
    LV(25),
    HE(26),
    IT(27),
    ET(29),
    AZ(30),
    PT(31);

    private final int value;
    Langue(int value) {
        this.value = value;
    }

    public static int fromValue(String name) {
        try {
            return Langue.valueOf(name.toUpperCase()).getValue();
        } catch (IllegalArgumentException e) {
            return 0; // community is not implemented yet.
        }
    }
}